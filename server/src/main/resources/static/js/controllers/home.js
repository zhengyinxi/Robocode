'use strict';

define([
    'angular'
], function (angular) {
    return ['$http', '$route', 'auth',
        function ($http, $route, auth) {
            var self = this;
            self.authProviders = auth.providers;
            self.user = "N/A";
            self.authenticated = false;

            $http.get('user').then(function (response) {
                var data = response.data;
                if (data.name) {
                    self.user = data.name;
                    self.authenticated = true;
                }
            }, function () {
            });

            self.logout = function () {
                $http.post('logout', {}).then(function () {
                    self.authenticated = false;
                    $route.reload();
                }, function () {
                });
            };
        }
    ];
});
