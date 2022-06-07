package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.exception.CustomerNotFoundException;
import ir.mvbdx.mywallet.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    private final JpaRepository<T, Long> baseRepository;

    @Override
    public T save(T base) {
        return baseRepository.save(base);
    }

    @Override
    public T findById(Long id) {
        return baseRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public T update(Long id, T base) {
        Optional<T> optionalBase = baseRepository.findById(id);
        if (optionalBase.isEmpty())
            throw new CustomerNotFoundException(id);
        return baseRepository.save(base);
    }

    @Override
    public boolean delete(Long id) {
        Optional<T> base = baseRepository.findById(id);
        if (base.isEmpty())
            throw new CustomerNotFoundException(id);
        baseRepository.deleteById(id);
        return true;
    }
}