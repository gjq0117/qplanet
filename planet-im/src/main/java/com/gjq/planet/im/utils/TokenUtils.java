package com.gjq.planet.im.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gjq.planet.common.constant.CommonConstant;
import com.gjq.planet.common.constant.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: gjq0117
 * @date: 2024/5/15 16:53
 * @description: token工具包
 */
@Component
@Slf4j
public class TokenUtils {

    /**
     * token秘钥，请勿泄露，请勿随便修改
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 解析token获取uid
     *
     * @param token
     * @return
     */
    public Long getUidOrNull(String token) {
        token = token.replace(CommonConstant.AUTHORIZATION_SCHEMA, "");
        Long uid = Optional.ofNullable(verifyToken(token))
                .map(map -> map.get(CommonConstant.UID_CLAIM))
                .map(Claim::asLong)
                .orElse(null);
        String redisToken = RedisUtils.getStr(getUserTokenKey(uid));
        if (Objects.nonNull(redisToken) && redisToken.equals(token)) {
            return uid;
        }
        return null;
    }

    /**
     * 通过uid获取token key
     *
     * @param uid
     * @return
     */
    public String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }

    /**
     * 解密Token
     *
     * @param token token
     * @return r
     */
    private Map<String, Claim> verifyToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (Exception e) {
            log.error("decode error,token:{}", token, e);
        }
        return null;
    }
}
