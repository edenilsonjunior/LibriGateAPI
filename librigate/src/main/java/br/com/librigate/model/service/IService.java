package br.com.librigate.model.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface genérica para serviços que lidam com entidades.
 *
 * @param <TEntity> Tipo da entidade
 * @param <TDTO> Tipo do DTO
 * @param <TPK> Tipo da chave primária
 */
public interface IService<TEntity, TDTO, TPK> {

    TEntity create(TDTO dto);

    TEntity update(TPK id, TDTO dto);

    Optional<TEntity> findByPK(TPK id);

    List<TEntity> findAll();

    void delete(TPK id);
}
