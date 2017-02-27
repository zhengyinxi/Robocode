'use strict';

define([
    'angular'
], function (angular) {
    var moduleName = 'AuthModule';
    angular
        .module(moduleName, [])
        .factory('auth', function ($q, $http, $window) {
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

                check: function () {
                    var dfd = $q.defer();
                    $http.get('user')
                        .then(function (response) {
                            dfd.resolve(response.data);
                        }, function () {
                            dfd.reject();
                        });
                    return dfd.promise;
                },

                login: function (name) {
                    var providers = this.providers;
                    var provider;
                    angular.forEach(providers, function (it) {
                        if (!provider && it.name === name) {
                            provider = it;
                        }
                    });

                    if (!provider) {
                        return;
                    }

                    $window.location.assign(provider.href);
                },

                logout: function () {
                    var dfd = $q.defer();
                    $http.post('logout', {})
                        .then(function () {
                            dfd.resolve();
                        }, function () {
                            dfd.reject();
                        });
                    return dfd.promise;
                }
            };
        });
    return moduleName;
});
