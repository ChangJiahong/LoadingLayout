package com.cjh.asdevtools.ex

/**
 * 当子控件太多时抛出
 * @author ChangJiahong
 * @date 2019/4/27
 */
class TooManyChildViewException : RuntimeException {
    constructor():super()

    constructor(msg: String): super(msg)
}