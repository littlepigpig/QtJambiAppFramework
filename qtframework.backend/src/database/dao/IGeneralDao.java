package database.dao;

import java.util.List;

public interface IGeneralDao
{
	void persist(Object entity);

	void remove(Object entity);

	Object findById(Long id);

	List<Object> findAll();
}

