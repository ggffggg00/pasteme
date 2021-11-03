/*
 * Format: http://www.debian.org/doc/packaging-manuals/copyright-format/1.0/
 * Comment: Distribution Compilation Copyright and License
 * Copyright: Copyright © 2017, Vladislav Borisov <ls@borisof.ru>
 * License: MIT
 *
 */

package ru.borisof.pasteme.app.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

public class CharIdGenerator implements IdentifierGenerator {

//    В качестве стандартной последовательности используем последовательность Hibernate
//    Можно использовать любую другую, например используемую для генерации внутреннего ID
    private final String SEQUENCE_NAME = "hibernate_sequence";


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object)
            throws HibernateException {
        int nextId = -1;
        try {
            nextId = getNextSeqValue(session.connection());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (nextId == -1)
            throw new IllegalArgumentException("Seq not accessible");



        return null;
    }

    private int getNextSeqValue(Connection connection) throws SQLException {
        ResultSet result;
        try {

            /*
             * Запрос на вытаскивание значения из последовательности БД может отличаться
             * в зависимости от диалекта. Реализация сейчас сделана для Postgres
             * В MySQL после каждой выборки очередного значения необходимо делать еще один запрос
             * для ее обновления
             * */
            result = connection.createStatement().executeQuery(String.format("SELECT  NEXTVAL('%s)", SEQUENCE_NAME));
        } catch (Exception e) {
                /*
                Сюда вылетает исключение, если последовательность не была найдена в БД
                Так же стоит быть аккуратным, потому что если использовать hibernate,
                то он автоматом создает последовательность для себ любимого
                Именно поэтому тут нет запроса на создание последовательности
                 */
            e.printStackTrace();
            return -1;
        }
        return result.next() ? result.getInt(1) : -1;
    }


}
