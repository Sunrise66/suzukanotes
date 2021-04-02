package com.sunrise.suzukanotes.common

import android.annotation.SuppressLint
import android.app.Application
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.TextUtils
import com.sunrise.suzukanotes.entity.db.RawSkill
import com.sunrise.suzukanotes.entity.db.RawAvailableSkillSet
import com.sunrise.suzukanotes.entity.db.RawCardData
import com.sunrise.suzukanotes.utils.FileUtils
import com.sunrise.suzukanotes.utils.LogUtils
import java.util.HashMap

/**
 *@author: JiangYu
 *Date: 2021/3/28
 *Time: 10:55
 *Email: jiangyu@haogroup.com
 */
class DBHelper(application: Application) :
    SQLiteOpenHelper(application, Static.DB_FILE_NAME, null, DB_VERSION) {
    companion object {
        const val DB_VERSION = 1

        @Volatile
        private lateinit var instance: DBHelper

        fun with(application: Application): DBHelper {
            synchronized(DBHelper::class.java) {
                instance = DBHelper(application)
            }
            return instance
        }

        @JvmStatic
        fun get(): DBHelper = instance
    }

    override fun onCreate(db: SQLiteDatabase) {}

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropAll(db)
        onCreate(db)
    }

    /***
     * 删除所有表
     * @param db
     */
    private fun dropAll(db: SQLiteDatabase) {
        val sqls: MutableList<String> =
            ArrayList()
        val op = "DROP TABLE IF EXISTS "
        for (field in this.javaClass.declaredFields) {
            if (field.name.startsWith("TABLE_NAME")) {
                try {
                    sqls.add(op + field[this])
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        }
        for (sql in sqls) {
            db.execSQL(sql)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> cursor2List(
        cursor: Cursor,
        theClass: Class<*>
    ): List<T>? {
        val result: MutableList<T> = mutableListOf()
        val arrField = theClass.declaredFields
        try {
            while (cursor.moveToNext()) {
                if (cursor.isBeforeFirst) {
                    continue
                }
                val bean = theClass.newInstance()
                for (f in arrField) {
                    val columnName = f.name
                    val columnIdx = cursor.getColumnIndex(columnName)
                    if (columnIdx != -1) {
                        if (!f.isAccessible) {
                            f.isAccessible = true
                        }
                        when (f.type) {
                            Byte::class.javaPrimitiveType -> {
                                f[bean] = cursor.getShort(columnIdx).toByte()
                            }
                            Short::class.javaPrimitiveType -> {
                                f[bean] = cursor.getShort(columnIdx)
                            }
                            Int::class.javaPrimitiveType -> {
                                f[bean] = cursor.getInt(columnIdx)
                            }
                            Long::class.javaPrimitiveType -> {
                                f[bean] = cursor.getLong(columnIdx)
                            }
                            String::class.java -> {
                                f[bean] = cursor.getString(columnIdx)
                            }
                            ByteArray::class.java -> {
                                f[bean] = cursor.getBlob(columnIdx)
                            }
                            Boolean::class.javaPrimitiveType -> {
                                f[bean] = cursor.getInt(columnIdx) == 1
                            }
                            Float::class.javaPrimitiveType -> {
                                f[bean] = cursor.getFloat(columnIdx)
                            }
                            Double::class.javaPrimitiveType -> {
                                f[bean] = cursor.getDouble(columnIdx)
                            }
                        }
                    }
                }
                result.add(bean as T)
            }
        } catch (e: InstantiationException) {
            e.printStackTrace()
            return null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return null
        } finally {
            cursor.close()
        }
        return result
    }

    /***
     * 准备游标
     * @param tableName 表名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValue WHERE [key] IN ([keyValue])
     * @return 存有数据的游标
     */
    @SuppressLint("Recycle")
    private fun prepareCursor(
        tableName: String,
        key: String?,
        keyValue: List<String>?
    ): Cursor? {
        if (!FileUtils.checkFile(FileUtils.getDbFilePath())) return null
        val db = readableDatabase ?: return null
        return if (key == null || keyValue == null || keyValue.isEmpty()) {
            db.rawQuery("SELECT * FROM $tableName ", null)
        } else {
            val paraBuilder = StringBuilder()
            paraBuilder.append("(")
            for (s in keyValue) {
                if (!TextUtils.isEmpty(s)) {
                    paraBuilder.append("?")
                    paraBuilder.append(", ")
                }
            }
            paraBuilder.delete(paraBuilder.length - 2, paraBuilder.length)
            paraBuilder.append(")")
            db.rawQuery(
                "SELECT * FROM $tableName WHERE $key IN $paraBuilder ",
                keyValue.toTypedArray()
            )
        }
    }


    /***
     * 由表名和类名无条件从数据库获取实体列表
     * @param tableName 表名
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
    </T> */
    private fun <T> getBeanList(
        tableName: String,
        theClass: Class<*>
    ): List<T>? {
        val cursor = prepareCursor(tableName, null, null) ?: return null
        return cursor2List(cursor, theClass)
    }

    /***
     * 由表名、类名、条件键值从数据库获取实体列表
     * @param tableName 表名
     * @param theClass 类名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValues WHERE [key] IN ([keyValue])
     * @param <T> theClass的类
     * @return 生成的实体列表
    </T> */
    private fun <T> getBeanList(
        tableName: String,
        theClass: Class<*>,
        key: String?,
        keyValues: List<String>?
    ): List<T>? {
        val cursor = prepareCursor(tableName, key, keyValues) ?: return null
        return cursor2List(cursor, theClass)
    }

    /***
     * 由表名、类名、条件键值从数据库获取单个实体
     * @param tableName 表名
     * @param theClass 类名
     * @param key WHERE [key] IN ([keyValue])
     * @param keyValue WHERE [key] IN ([keyValue])
     * @param <T> theClass的类
     * @return 生成的实体
    </T> */
    private fun <T> getBean(
        tableName: String,
        theClass: Class<*>,
        key: String?,
        keyValue: String
    ): T? {
        val cursor =
            prepareCursor(tableName, key, listOf(keyValue)) ?: return null
        val data: List<T>? = cursor2List(cursor, theClass)
        return if (data?.isNotEmpty() == true) data[0] else null
    }

    /***
     * 由SQL语句、SQL中的键值从数据库获取单个实体
     * @param sql SQL语句
     * @param keyValue IN (?) => ?=keyValue
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体
    </T> */
    @SuppressLint("Recycle")
    private fun <T> getBeanByRaw(
        sql: String?,
        theClass: Class<*>
    ): T? {
        if (!FileUtils.checkFile(FileUtils.getDbFilePath())) return null
        try {
            val cursor =
                readableDatabase.rawQuery(sql, null) ?: return null
            val data: List<T>? = cursor2List(cursor, theClass)
            return if (data?.isNotEmpty() == true) data[0] else null
        } catch (e: Exception) {
            LogUtils.file(LogUtils.E, "getBeanByRaw", e.message, e.stackTrace)
            return null
        }
    }

    /***
     * 由SQL语句无条件从数据库获取实体列表
     * @param sql SQL语句
     * @param keyValue 替换？的值
     * @param theClass 类名
     * @param <T> theClass的类
     * @return 生成的实体列表
    </T> */
    @SuppressLint("Recycle")
    private fun <T> getBeanListByRaw(
        sql: String?,
        theClass: Class<*>
    ): List<T>? {
        if (!FileUtils.checkFile(FileUtils.getDbFilePath())) return null
        try {
            val cursor =
                readableDatabase.rawQuery(sql, null) ?: return null
            return cursor2List(cursor, theClass)
        } catch (e: Exception) {
            LogUtils.file(LogUtils.E, "getBeanListByRaw", e.message, e.stackTrace)
            return null
        }
    }

    /***
     * 删除表
     * @param tableName
     * @return
     */
    fun dropTable(tableName: String): Boolean {
        return try {
            writableDatabase.execSQL("DROP TABLE IF EXISTS $tableName")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /***
     * 获取查询语句的第一行第一列值
     * @param sql
     * @return
     */
    private fun getOne(sql: String?): String? {
        if (!FileUtils.checkFile(FileUtils.getDbFilePath())) return null
        val cursor = readableDatabase.rawQuery(sql, null)
        cursor.moveToNext()
        val result = cursor.getString(0)
        cursor.close()
        return result
    }

    /***
     * 获取 int-string map
     * @param sql
     * @return
     */
    private fun getIntStringMap(
        sql: String,
        key: String?,
        value: String?
    ): Map<Int, String>? {
        if (!FileUtils.checkFile(FileUtils.getDbFilePath())) return null
        val cursor = readableDatabase.rawQuery(sql, null)
        val result: MutableMap<Int, String> = HashMap()
        while (cursor.moveToNext()) {
            result[cursor.getInt(cursor.getColumnIndex(key))] =
                cursor.getString(cursor.getColumnIndex(value))
        }
        cursor.close()
        return result
    }

    /*********************************************************************************************************/

    fun getRawCardDatas(): List<RawCardData>? {
        return getBeanListByRaw(
            """
                select cd.id,
                       cd.chara_id,
                       cd.available_skill_set_id,
                       cd.talent_speed,
                       cd.talent_stamina,
                       cd.talent_pow,
                       cd.talent_guts,
                       cd.talent_wiz,
                       cd.talent_group_id,
                       cd.bg_id,
                       cd.running_style,
                       group_concat(td.text, ';') details
                from card_data cd
                         left join text_data td on cd.chara_id = td."index"
                where td.category in (6, 7, 8, 9, 144, 157, 158, 162, 163, 164, 165, 166, 167, 168, 169)
                group by td."index"
            """.trimIndent(),
            RawCardData::class.java
        )
    }

    fun getRawAvailableSkillSet(id: Int): RawAvailableSkillSet? {
        return getBeanByRaw<RawAvailableSkillSet>(
            """
                select available_skill_set_id,
                       group_concat(skill_id, ';') skill_ids
                from available_skill_set
                where available_skill_set_id = $id
                group by available_skill_set_id
            """.trimIndent(),
            RawAvailableSkillSet::class.java
        )
    }

    fun getRawSkill(id: Int): RawSkill? {
        return getBeanByRaw<RawSkill>(
            """
                select sd.*,
                group_concat(td.text,';') details
                from skill_data sd
                         left join text_data td on sd.id = td."index"
                where sd.id = $id and td.category in (47,48)
            """.trimIndent(),
            RawSkill::class.java
        )
    }
}