package com.lx.treasure.common.utils;

import com.lx.treasure.bean.common.DataId;
import com.lx.treasure.bean.common.DataIdProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于生成数据的id
 * 1. 有一个固定的时间戳：2000年01月01日 0时00分00秒
 * 2. 获取当前时间戳 - 固定时间戳获取到一个新的long类型数据
 * 3. 城市代码/机房代码/机架代码/机器代码/时间戳位数/并发数 -- 3/4/5/3/41/8
 * 4. 加了palses 表示指定的数字的位数
 */

@SuppressWarnings("WeakerAccess")
@Component
public class IdUtils {

    private long cityNum = 7;
    private long machineRoomNum = 15;
    private long machineFrameNum = 31;
    private long machineNum = 7;
    private long cityNumPlaces = 3;
    private long machineRoomNumPlaces = 4;
    private long machineFrameNumPlaces = 5;
    private long machineNumPlaces = 3;
    private long timeMillsPlaces = 41;
    private long concurrencePlaces = 8;
    private String fixedTime = "2020-01-01";

    // 配置文件
    DataIdProp dataIdProp;

    // id的头部
    private String ID_HEAD;
    // 初始时间戳的时间
    private Long INIT_TIME_MILLS;
    // 一毫秒内的id
    private AtomicInteger orderId = new AtomicInteger();
    // 修改后当前时间毫秒数
    private String currentTime = "";

    // 装载参数+初始化参数
    @Autowired
    public IdUtils(DataIdProp dataIdProp) {
        this.dataIdProp = dataIdProp;
        loadProp();
        initProp();
    }

    // 装载配置文件中的参数
    private void loadProp() {
        cityNum = dataIdProp.getCityNum();
        machineRoomNum = dataIdProp.getMachineRoomNum();
        machineFrameNum = dataIdProp.getMachineFrameNum();
        machineNum = dataIdProp.getMachineNum();
        cityNumPlaces = dataIdProp.getCityNumPlaces();
        machineRoomNumPlaces = dataIdProp.getMachineRoomNumPlaces();
        machineFrameNumPlaces = dataIdProp.getMachineFrameNumPlaces();
        machineNumPlaces = dataIdProp.getMachineNumPlaces();
        timeMillsPlaces = dataIdProp.getTimeMillsPlaces();
        concurrencePlaces = dataIdProp.getConcurrencePlaces();
        fixedTime = dataIdProp.getFixedTime();
    }

    // 装载参数后初始化对象
    private void initProp() {
        String cityNumStr = longToString(cityNum,cityNumPlaces);
        String machineRoomNumStr = longToString(machineRoomNum,machineRoomNumPlaces);
        String machineFrameNumStr = longToString(machineFrameNum,machineFrameNumPlaces);
        String machineNumStr = longToString(machineNum,machineNumPlaces);
        ID_HEAD = cityNumStr + machineRoomNumStr + machineFrameNumStr + machineNumStr;
        INIT_TIME_MILLS = DateUtils.stringToLong(fixedTime);
    }

    // 根据long和位数转为String 主要在于为long前面补上0
    // 1 3 ---> 001 | 0 3 ---> 000 | 7 3 --> 111
    private String longToString(long num,long count) {
        StringBuilder numStr = new StringBuilder(Long.toBinaryString(num));
        for (int i = numStr.length(); i < count; i++)
            numStr.insert(0, '0');
        return numStr.toString();
    }

    /**
     * 生成id
     *  1. 获取当前时间戳
     *  2. 减去 固定时间戳
     *  3. 转为 41位二进制数(补0）
     *  4. 获取当前这一秒内的编号，并转为二进制string
     *  5. 将头尾结合使用Long.parseUnsignedLong(num,2) 转为long类型
     * @return long类型数据
     */
    public Long generateId() {
        Long time = Calendar.getInstance().getTimeInMillis();
        time -= INIT_TIME_MILLS;
        String timeStr = longToString(time, timeMillsPlaces);
        if (!timeStr.equals(currentTime)) {
            // 当前时间和上次获取id时间不一样，需要更新当前时间，并归零计数器
            currentTime = timeStr;
            orderId.set(0);
        }
        long orderNum = orderId.addAndGet(1);
        String orderStr = longToString(orderNum, concurrencePlaces);
        String idStr = ID_HEAD + timeStr + orderStr;
        return Long.parseUnsignedLong(idStr, 2);
    }

    /**
     * 根据id反推各个参数：
     * 城市id/机房id/机架id/机器id/发送时间/发送序号
     * 1. 将long转为二进制string
     * 2. 依次拆分各个部分
     * 3. 将各个部分转为long
     * 4. 将时间戳还原为正常时间戳
     * 5. 将正常时间戳还原为时间对象
     * @param id id
     */
    public DataId parseId(long id) {
        DataId dataId = new DataId();
        String idStr = longToString(id, 64);
        int lastPlaces = 0;
        int places = (int) (lastPlaces + cityNumPlaces);
        dataId.setCityNum(Long.parseUnsignedLong(idStr.substring(lastPlaces, places),2));
        lastPlaces = places;
        places += machineRoomNumPlaces;
        dataId.setMachineRoomNum(Long.parseUnsignedLong(idStr.substring(lastPlaces, places),2));
        lastPlaces = places;
        places += machineFrameNumPlaces;
        dataId.setMachineFrameNum(Long.parseUnsignedLong(idStr.substring(lastPlaces, places),2));
        lastPlaces = places;
        places += machineNumPlaces;
        dataId.setMachineNum(Long.parseUnsignedLong(idStr.substring(lastPlaces, places),2));
        lastPlaces = places;
        places += timeMillsPlaces;
        long timeMillsNum = Long.parseUnsignedLong(idStr.substring(lastPlaces, places), 2);
        lastPlaces = places;
        places += concurrencePlaces;
        dataId.setOrderNum(Long.parseUnsignedLong(idStr.substring(lastPlaces, places), 2));
        // 将时间戳还原为正常Date对象
        timeMillsNum += INIT_TIME_MILLS;
        dataId.setTimeMillsNum(new Date(timeMillsNum));
        return dataId;
    }

    // 根据配置文件获取到程序的机器代码
    public static void main(String[] args) {
        long a = Long.MIN_VALUE;
        System.out.println(Long.MIN_VALUE);
        System.out.println(Long.toBinaryString(a));
        System.out.println(Long.parseUnsignedLong(Long.toBinaryString(a),2));
        IdUtils idUtils = new IdUtils(new DataIdProp());
        System.out.println(idUtils.ID_HEAD);
        long id = idUtils.generateId();
        System.out.println(id);
        System.out.println(idUtils.parseId(id));
    }
}
