package com.study.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolWriper {
		//Redis连接池对象
		private JedisPool jedisPool;
		
		public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,
				final int port,final int timeout,final String password) {
			try {
				//使用新的连接方法 接如密码字段 配置文件新增信息
				//jedisPool = new JedisPool(poolConfig,host,port);
				jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 获取Redis连接池对象
		 * @return
		 */
		public JedisPool getJedisPool() {
			return jedisPool;
		}

		/**
		 * 注入Redis连接池对象
		 * @param jedisPool
		 */
		public void setJedisPool(JedisPool jedisPool) {
			this.jedisPool = jedisPool;
			
	}
}