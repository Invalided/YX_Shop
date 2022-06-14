package com.study.o2o.dto;

import java.util.List;

import com.study.o2o.entity.localAuth;
import com.study.o2o.enums.localAuthStateEnum;

public class localAuthExecution {
		// 结果状态
		private int state;
		// 状态标识
		private String stateInfo;
		//总数
		private int count;

		private localAuth localAuth;

		private List<localAuth> localAuthList;

		public localAuthExecution() {
		}

		// 失败的构造器
		public localAuthExecution(localAuthStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
		}

		// 成功的构造器
		public localAuthExecution(localAuthStateEnum stateEnum, localAuth localAuth) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.localAuth = localAuth;
		}

		// 成功的构造器
		public localAuthExecution(localAuthStateEnum stateEnum,
				List<localAuth> localAuthList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.localAuthList = localAuthList;
		}
		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public String getStateInfo() {
			return stateInfo;
		}

		public void setStateInfo(String stateInfo) {
			this.stateInfo = stateInfo;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public localAuth getLocalAuth() {
			return localAuth;
		}

		public void setLocalAuth(localAuth localAuth) {
			this.localAuth = localAuth;
		}

		public List<localAuth> getLocalAuthList() {
			return localAuthList;
		}

		public void setLocalAuthList(List<localAuth> localAuthList) {
			this.localAuthList = localAuthList;
		}
		
}
