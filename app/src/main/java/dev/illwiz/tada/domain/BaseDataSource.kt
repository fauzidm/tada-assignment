package dev.illwiz.tada.domain

/**
 * We define method in BaseDataSource if the method
 * would be interacting w both Local and Remote data source
 * If a method isn't interacting w both Local and Remote
 * consider to make the method part of Local or Remote instead
 */
abstract class BaseDataSource {

    /**
     * Local = data from DB
     */
    abstract class Local {

    }

    /**
     * Remote = data from API
     */
    abstract class Remote {

    }
}