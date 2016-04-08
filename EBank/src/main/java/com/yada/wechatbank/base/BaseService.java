package com.yada.wechatbank.base;

import java.util.List;

public abstract class BaseService<Entity, Id> {

	protected abstract BaseDao<Entity, Id> getBaseDao();
	
	public void insert(Entity entity) {
		
		getBaseDao().insert(entity);
	}

	public void update(Entity entity) {
		
		getBaseDao().update(entity);
	}

	public void delete(Id id) {
		getBaseDao().delete(id);
	}

	public Entity getById(Id id) {
		return getBaseDao().getById(id);
	}

	public int findCountByWhere(Entity entity) {
		return getBaseDao().findCountByWhere(entity);
	}

	public List<Entity> findListByWhere(Entity entity) {
		return getBaseDao().findListByWhere(entity);
	}
}
