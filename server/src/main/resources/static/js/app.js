'use strict';

require([
    'angular'
], function (angular) {
    require([
        'controllers/home',
        'services/auth',
        'services/trans',
        'angular-animate',
        'angular-route'
    ], function (homeCtrl, authSrv, transSrv) {
        angular
            .module('app', [authSrv, transSrv, 'ngAnimate', 'ngRoute'])
            .config(function ($qProvider, $routeProvider, $locationProvider) {
                $qProvider.errorOnUnhandledRejections(false);
                $locationProvider.html5Mode({
                    enabled: false
                }).hashPrefix('!');
            })
            .controller('HomeController', homeCtrl);
        angular.element(function () {
            angular.bootstrap(document, ['app']);
        });
    });
});
