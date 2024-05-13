package com.gjq.planet.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gjq.planet.common.constant.CommonConstant;
import com.gjq.planet.common.constant.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: gjq0117
 * @date: 2024/4/14 15:24
 * @description: token工具
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
     * JWT生成Token
     *
     * @param uid
     * @return
     */
    public String createToken(Long uid) {
        // build token
        String token = JWT.create()
                // 只存一个uid信息，其他的自己去redis查
                .withClaim(CommonConstant.UID_CLAIM, uid)
                .withClaim(CommonConstant.CREATE_TIME, new Date())
                // signature
                .sign(Algorithm.HMAC256(secret));
        // 将token存放到redis
        RedisUtils.set(getUserTokenKey(uid), token, RedisKey.TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
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
     * 通过token获取redis中的uid信息，没有则证明信息过期，需要重新登录
     *
     * @param token
     * @return
     */
    public Long getValidUid(String token) {
        Long uid = getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        String oldToken = getRedisUserToken(uid);
        // redis中的token和客户端传过来的token相同则返回uid
        return Objects.equals(oldToken, token) ? uid : null;
    }

    /**
     * 获取redis中的userToken
     *
     * @param uid
     * @return
     */
    public String getRedisUserToken(Long uid) {
        return RedisUtils.getStr(getUserTokenKey(uid));
    }

    /**
     * 移除redis中的userToken
     *
     * @param uid
     */
    public void removeRedisUserToken(Long uid) {
        RedisUtils.del(getUserTokenKey(uid));
    }

    /**
     * 解析request获取token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String header = request.getHeader(CommonConstant.HEADER_AUTHORIZATION);
        return Optional.ofNullable(header)
                .filter(h -> h.startsWith(CommonConstant.AUTHORIZATION_SCHEMA))
                .map(h -> h.replace(CommonConstant.AUTHORIZATION_SCHEMA, ""))
                .orElse(null);
    }

    /**
     * 解析token获取uid
     *
     * @param token
     * @return
     */
    private Long getUidOrNull(String token) {
        return Optional.ofNullable(verifyToken(token))
                .map(map -> map.get(CommonConstant.UID_CLAIM))
                .map(Claim::asLong)
                .orElse(null);
    }

    /**
     * 通过请求解析token并获取uid
     *
     * @param request
     * @return
     */
    public Long getUidOrNull(HttpServletRequest request) {
        return getValidUid(getToken(request));
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
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
