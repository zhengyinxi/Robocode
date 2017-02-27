'use strict';

define([
    'angular'
], function (angular) {
    var moduleName = 'AuthModule';
    angular
        .module(moduleName, [])
        .factory('auth', function ($http, $window) {
            return {
                providers: [
                    {
                        _order: 1,
                        name: 'Facebook',
                        href: 'login/facebook'
                    },
                    {
                        _order: 2,
                        name: 'Github',
                        href: 'login/github'
                    },
                    {
                        _order: 3,
                        name: 'Microsoft',
                        href: 'login/microsoft'
                    }
                ],

                check: function (success, error) {
                    $http.get('user').then(success, error);
                },

                login: function (name) {
                    var provider;
                    angular.forEach(this.providers, function(p) {
                        if (!provider && p.name === name) {
                            provider = p;
                        }
                    });

                    if (!provider) {
                        return;
                    }

                    $window.location.assign(provider.href);
                },

                logout: function(success, error) {
                    $http.post('logout', {}).then(success, error);
                }
            };
        });
    return moduleName;
});
