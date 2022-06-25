package ru.zimins.foodorder.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.webjars.NotFoundException;

import java.util.List;

/**
 * Базовый интерфейс для CRUD сервисов
 * Содержит стандартный набор методов для создания/чтения/обновления/удаления
 *
 * @param <T> тип сущности, с которой работает сервис
 * @param <ID> тип идентификатора сущности
 */
public interface CrudService<T, ID> {

    /**
     * Создание сущности
     *
     * @param model модель сущности для создания
     * @return созданная сущность
     */
    default T create(T model) {
        throw new UnsupportedOperationException();
    }

    /**
     * Получение полного списка сущностей
     *
     * @return список сущностей
     */
    default List<T> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Поиск сущности по идентификатору
     *
     * @param id идентификатор предполагаемой сущности
     * @return найденная сущность
     * @throws NotFoundException исключение, если сущность не была найдена
     */
    default T findById(ID id) throws NotFoundException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param pageable набор параметров для поиска страницы сущностей
     * @return станица сущностей
     */
    default Page<T> findPage(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    /**
     * Обновление сущности
     *
     * @param model модель сущности для обновления
     * @return созданная сущность
     */
    default T update(T model) {
        throw new UnsupportedOperationException();
    }

    /**
     * Удаление сущности по идентификатору
     *
     * @param id идентификатор сущности
     * @return удаленная сущность
     */
    default T deleteById(ID id) {
        throw new UnsupportedOperationException();
    }
}
