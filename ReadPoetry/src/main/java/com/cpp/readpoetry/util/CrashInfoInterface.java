package com.cpp.readpoetry.util;

/**
 * app闪退信息捕获接口
 */
public interface CrashInfoInterface {
    // 获取日志名称
    String createLogName();

    // 记录app信息
    String recordAppInfo();
}
