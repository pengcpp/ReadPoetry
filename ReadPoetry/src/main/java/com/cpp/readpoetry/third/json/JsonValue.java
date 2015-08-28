package com.cpp.readpoetry.third.json;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author wenhua.li 2008-12-30 <br>
 *         Json format of value.
 */
public abstract class JsonValue implements Serializable {

    /**
     * Map类型
     */
    protected final static int TYPE_OBJECT = 1;

    /**
     * 对象数组
     */
    protected final static int TYPE_ARRAY = 2;

    /**
     * String类型
     */
    protected final static int TYPE_STRING = 3;

    /**
     * boolean类型
     */
    protected final static int TYPE_BOOL = 4;

    /**
     * 字节数组
     */
    protected final static int TYPE_BYTES = 5;

    /**
     * num类型
     */
    protected final static int TYPE_NUM = 6;

    /**
     * null类型
     */
    protected final static int TYPE_NULL = 7;

    public abstract String toJsonString();

    public abstract String toNormalString();

    @Override
    public abstract String toString();

    /**
     * 写入对象
     * 
     * @param dos
     */
    protected abstract void write(DataOutputStream dos) throws IOException;

    /**
     * 读取对象
     * 
     * @param dis
     */
    protected abstract void read(DataInputStream dis) throws IOException;

    /**
     * Parse a String to a JsonValue.
     * 
     * @param s
     *            a String
     * @return a JsonValue
     */
    protected static JsonValue parseValue(String s) {
        if (s == null || s.equals("null")) {
            return new JsonNull();
        }
        final int len = s.length();
        char curChar = 0;
        int sIdx = -1, eIdx;
        while (++sIdx < len && (curChar = s.charAt(sIdx)) < 33)
            ;
        sIdx++;
        if (curChar == '"') {
            eIdx = s.lastIndexOf('"');
            if (eIdx > -1) {
                String str = s.substring(sIdx, eIdx);
                return JsonString.parseString(str);
            } else {
                System.err.println("'\"' is expected to close a string!");
            }
        } else if ((curChar > 47 && curChar < 58) || curChar == '-') {
            return JsonNum.parseNum(s);
        } else if (curChar == '{') {
            eIdx = s.lastIndexOf('}');
            if (eIdx > -1) {
                String str = s.substring(sIdx, eIdx);
                return JsonObject.parseObject(str);
            } else {
                System.err.println("'}' is expected to close a JsonObject!");
            }
        } else if (curChar == '[') {
            eIdx = s.lastIndexOf(']');
            if (eIdx > -1) {
                String str = s.substring(sIdx, eIdx);
                return JsonArray.parseArray(str);
            } else {
                System.err.println("']' is expected to close a JsonArray!");
            }
        } else if (curChar == 't' || curChar == 'T' || curChar == 'f'
                || curChar == 'F') {
            return JsonBool.parseBool(s);
        }
        return null;
    }

    /**
     * 从数据流中读取一个被序列化的对象
     * 
     * @param dis
     *            输入流
     * @return 序列化对象
     * @throws IOException
     *             IO异常或其他异常
     */
    protected static JsonValue readObject(DataInputStream dis)
            throws IOException {
        JsonValue val = null;
        int type = dis.read();
        switch (type) {
        case TYPE_OBJECT:
            val = new JsonObject();
            break;
        case TYPE_ARRAY:
            val = new JsonArray();
            break;
        case TYPE_STRING:
            val = new JsonString();
            break;
        case TYPE_BOOL:
            val = new JsonBool();
            break;
        case TYPE_BYTES:
            val = new JsonBytes();
            break;
        case TYPE_NUM:
            val = new JsonNum();
            break;
        case TYPE_NULL:
            val = new JsonNull();
            break;
        default:
            return null;
        }
        val.read(dis);
        return val;
    }

    /**
     * 将需要被序列化的对象写入流
     * 
     * @param val
     *            需要被序列化的对象
     * @param dos
     *            输出流
     * @throws IOException
     *             IO异常或其他异常
     */
    protected static void writeObject(JsonValue val, DataOutputStream dos)
            throws IOException {
        val.write(dos);
    }
}
