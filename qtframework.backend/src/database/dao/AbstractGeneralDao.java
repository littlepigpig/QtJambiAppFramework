package database.dao;

import java.util.List;

public class AbstractGeneralDao implements IGeneralDao
{


	@Override
	public List<Object> findAll() {
		return null;
	}

	@Override
	public Object findById(final Long id) {
		return null;
	}

	@Override
	public void persist(final Object entity) {
	}

	@Override
	public void remove(final Object entity) {
	}
}
