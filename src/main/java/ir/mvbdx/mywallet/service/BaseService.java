package ir.mvbdx.mywallet.service;

import java.util.List;

public interface BaseService<T> {
    T save(T base);

    T findById(Long id);

    List<T> findAll();

    T update(Long id, T base);

    boolean delete(Long id);
}
