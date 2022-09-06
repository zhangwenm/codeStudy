package com.tuling.circulardependencies;

import org.springframework.beans.BeansException;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@FunctionalInterface
public interface ObjectFactory<T> {

    /**
     * Return an instance (possibly shared or independent)
     * of the object managed by this factory.
     * @return the resulting instance
     * @throws BeansException in case of creation errors
     */
    T getObject() throws BeansException;

}
