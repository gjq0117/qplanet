package com.gjq.planet.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gjq.planet.common.domain.vo.req.CursorPageBaseReq;
import com.gjq.planet.common.domain.vo.resp.CursorPageBaseResp;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author: gjq0117
 * @date: 2024/5/24 12:27
 * @description: 游标翻页工具
 */
public class CursorUtils {

    public static  <T> CursorPageBaseResp<T> getCursorPageByMysql(IService<T> mapper, CursorPageBaseReq request, Consumer<LambdaQueryWrapper<T>> initWrapper, SFunction<T, ?> cursorColumn, Boolean isAsc) {
        // 游标类型
        Class<?> cursorType = LambdaUtils.getReturnType(cursorColumn);
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        // 配置游标
        if (StringUtils.isNotBlank(request.getCursor())) {
            wrapper.lt(cursorColumn, parseCursor(request.getCursor(), cursorType));
        }
        // 格外条件
        initWrapper.accept(wrapper);
        // 游标方向
        if (isAsc) {
            wrapper.orderByAsc(cursorColumn);
        } else {
            wrapper.orderByDesc(cursorColumn);
        }
        // 分页查询
        Page<T> page = mapper.page(request.initPage(), wrapper);
        // 取出游标
        String cursor = Optional.ofNullable(CollectionUtil.getLast(page.getRecords()))
                .map(cursorColumn)
                .map(CursorUtils::toCursor)
                .orElse(null);
        //判断是否最后一页
        Boolean isLast = page.getRecords().size() != request.getPageSize();
        return new CursorPageBaseResp<>(cursor, isLast, page.getRecords());
    }

    private static String toCursor(Object o) {
        if (o instanceof Date) {
            return String.valueOf(((Date) o).getTime());
        } else {
            return o.toString();
        }
    }

    private static Object parseCursor(String cursor, Class<?> cursorType) {
        if (Date.class.isAssignableFrom(cursorType)) {
            return new Date(Long.parseLong(cursor));
        } else {
            return cursor;
        }
    }
}
