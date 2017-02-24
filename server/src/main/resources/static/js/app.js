'use strict';

require([
    'angular'
], function (angular) {
    require([
        'controllers/home',
        'services/auth',
        'angular-animate',
        'angular-route'
    ], function (homeCtrl, authSrv) {
        angular
            .module('app', [authSrv, 'ngAnimate', 'ngRoute'])
            .config(function ($routeProvider, $locationProvider) {
                $locationProvider.html5Mode({
                    enabled: false
                }).hashPrefix('*');
            })
            .controller('HomeController', homeCtrl);
        angular.element(function () {
            angular.bootstrap(document, ['app']);
        });
    });
});
