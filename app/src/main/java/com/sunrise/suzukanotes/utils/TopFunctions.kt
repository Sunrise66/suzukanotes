package com.sunrise.suzukanotes.utils

/**
 *@author: Sunrise
 *Date: 2021/4/6
 *Time: 14:27
 *Email: e1175132893@outlook.com
 */
fun <T> beanMapper(beanClass: Class<T>, rawObject: Any): T {
    val bean = beanClass.newInstance()
    val beanFields = beanClass.declaredFields
    val rawFields = rawObject::class.java.declaredFields
    beanFields.forEach { f ->
        if (!f.isAccessible) {
            f.isAccessible = true
        }
        rawFields.forEach {
            if (f.name == it.name) {
                if (!it.isAccessible) {
                    it.isAccessible = true
                }
                if (f.type == it.type) {
                    f[bean] = it[rawObject]
                }
            }
        }
    }
    return bean
}