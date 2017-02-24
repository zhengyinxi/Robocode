'use strict';

define([
    'angular'
], function (angular) {
    var moduleName = 'AuthModule';
    angular
        .module(moduleName, [])
        .factory('auth', function () {
            return {
                providers: [
                    {
                        id: 1,
                        name: 'Facebook',
                        href: 'login/facebook'
                    },
                    {
                        id: 2,
                        name: 'Github',
                        href: 'login/github'
                    },
                    {
                        id: 3,
                        name: 'Microsoft',
                        href: 'login/microsoft'
                    }
                ],

                check: function () {
                    console.log('check');
                },

                login: function () {
                    console.log('login');
                    return {};
                },

                logout: function (todos) {
                    console.log('logout');
                    return {};
                }
            };
        });
    return moduleName;
});
