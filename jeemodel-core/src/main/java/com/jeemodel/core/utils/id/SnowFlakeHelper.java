package com.jeemodel.core.utils.id;

/**
 *  twitter的snowflake算法 -- java实现
 *  协议格式： 0 - 41位时间戳 - 5位数据中心标识 - 5位机器标识 - 12位序列号
 * @author Rootfive
 */
public class SnowFlakeHelper {

	/**
	 * 起始的时间戳，可以修改为服务第一次启动的时间
	 * 一旦服务已经开始使用，起始时间戳就不应该改变
	 * 北京时间:2022-06-01 00:00:01 
	 * 时间戳:1654012801000 毫秒(ms)
	 */
	private final static long START_STMP = 1654012801000L;

	/**
	 * 每一部分占用的位数
	 */
	private final static long SEQUENCE_BIT = 12; // 序列号占用的位数
	private final static long MACHINE_BIT = 5; // 机器标识占用的位数
	private final static long DATACENTER_BIT = 5;// 数据中心占用的位数

	/**
	 * 每一部分的最大值
	 */
	private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
	private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
	private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

	/**
	 * 每一部分向左的位移
	 */
	private final static long MACHINE_LEFT = SEQUENCE_BIT;
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

	private long datacenterId; // 数据中心
	private long machineId; // 机器标识
	private long sequence = 0L; // 序列号
	private long lastStmp = -1L;// 上一次时间戳

	
	/**
	 * 单机默认使用
	 */
	private final static SnowFlakeHelper SINGLE_DEFAULT_SNOWFLAKE_HELPER = new SnowFlakeHelper(31, 31);
	
	
	/**
	 * 通过单例模式来获取实例
	 * 分布式部署服务时，数据节点标识和机器标识作为联合键必须唯一
	 * @param datacenterId 数据节点标识ID 取值范围：0-31
	 * @param machineId 机器标识ID  取值范围：0-31
	 */
	public SnowFlakeHelper(long datacenterId, long machineId) {
		if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
			throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
		}
		if (machineId > MAX_MACHINE_NUM || machineId < 0) {
			throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
		}
		this.datacenterId = datacenterId;
		this.machineId = machineId;
	}

	/**
	 * 产生下一个ID
	 *
	 * @return
	 */
	public synchronized long nextId() {
		long currStmp = getNewstmp();
		if (currStmp < lastStmp) {
			throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
		}

		if (currStmp == lastStmp) {
			// 相同毫秒内，序列号自增
			sequence = (sequence + 1) & MAX_SEQUENCE;
			// 同一毫秒的序列数已经达到最大
			if (sequence == 0L) {
				currStmp = getNextMill();
			}
		} else {
			// 不同毫秒内，序列号置为0
			sequence = 0L;
		}

		lastStmp = currStmp;

		return (currStmp - START_STMP) << TIMESTMP_LEFT // 时间戳部分
				| datacenterId << DATACENTER_LEFT // 数据中心部分
				| machineId << MACHINE_LEFT // 机器标识部分
				| sequence; // 序列号部分
	}
	
	
	public static synchronized long fastNextId() {
		return SINGLE_DEFAULT_SNOWFLAKE_HELPER.nextId();
	}

	private long getNextMill() {
		long mill = getNewstmp();
		while (mill <= lastStmp) {
			mill = getNewstmp();
		}
		return mill;
	}

	private final long getNewstmp() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
		SnowFlakeHelper snowFlakeHelper = new SnowFlakeHelper(2, 3);
		long start = System.currentTimeMillis();
		for (int i = 0; i < (1 << 18); i++) {
			System.out.println(i + ": " + snowFlakeHelper.nextId());
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
