package br.com.librigate.model.service.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface genérica para serviços que lidam com entidades.
 *
 * @param <TEntity> Tipo da entidade
 * @param <TRequest> Corpo da requisição
 * @param <TPK> Tipo da chave primária
 */
public interface IService<TEntity, TRequest, TPK> {

    TEntity create(TRequest request);

    TEntity update(TRequest request);

    Optional<TEntity> findByPK(TPK id);

    List<TEntity> findAll();

    void delete(TPK id);
}
