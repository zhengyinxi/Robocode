'use strict';

define([
    'angular'
], function (angular) {
    return ['$http', '$window', 'auth', 'transfer',
        function ($http, $window, auth, trans) {
            var self = this;
            self.authProviders = auth.providers;
            self.user = "N/A";
            self.authenticated = false;

            auth.check(function (response) {
                var data = response.data;
                if (data.name) {
                    self.user = data.name;
                    self.authenticated = true;
                }
            }, function () {
            });

            self.logout = function () {
                auth.logout(function () {
                    self.authenticated = false;
                    $window.location.reload();
                }, function () {
                });
            }


        }
    ];
});
