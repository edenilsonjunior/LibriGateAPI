package br.com.librigate.model.mapper.interfaces;

/**
 * Interface genérica para serviços que usam mappers.
 *
 * @param <TEntity> Tipo da entidade
 * @param <TDTO> Tipo do DTO
 */
public interface IMapper <TEntity, TDTO> {

    TEntity toEntity(TDTO dto);

    TDTO toDTO(TEntity entity);
}
