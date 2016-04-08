package com.yada.wechatbank.base;

import java.util.List;

public interface BaseDao<Entity,Id> {

	// 插入
	abstract void insert(Entity entity);

	// 更新
	abstract void update(Entity entity);

	// 删除
	abstract void delete(Id id);

	// 根据ID查询
	abstract Entity getById(Id id);

	// 根据条件查询数据数据COUNT
	abstract int findCountByWhere(Entity entity);

	// 根据条件查询数据
	abstract List<Entity> findListByWhere(Entity entity);
	
}
