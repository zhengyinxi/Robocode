'use strict';

define([
    'angular'
], function (angular) {
    return ['$http', '$window', '$timeout', 'auth', 'trans',
        function ($http, $window, $timeout, auth, trans) {
            var self = this;
            self.authProviders = auth.providers;
            self.user = "N/A";
            self.authenticated = false;

            auth.check()
                .then(function (data) {
                    if (data.name) {
                        self.user = data.name;
                        self.authenticated = true;
                    }
                }, function () {
                });

            self.logout = function () {
                auth.logout()
                    .then(function () {
                        self.authenticated = false;
                        $window.location.reload();
                    }, function () {
                    });
            }

            trans.connect()
                .then(function () {
                    trans.sendName('wtf');
                });
            $timeout(function () {
                trans.disconnect();
            }, 10000);
        }
    ];
});
