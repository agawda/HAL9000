package com.javaacademy.robot.converters;

import java.util.List;

public interface DtoEntityConverter<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    List<D> toDtos(List<E> entities);
}
