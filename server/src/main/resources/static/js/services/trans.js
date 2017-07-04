'use strict';

define([
    'angular',
    'sockjs-client',
    'stomp-websocket'
], function (angular, SockJS, Stomp) {
    var moduleName = 'TransModule';
    angular
        .module(moduleName, [])
        .factory('trans', function ($q, $window) {
            return {
                _client: undefined,

                connected: false,

                connect: function () {
                    var dfd = $q.defer();
                    var self = this;
                    var client = self._client = Stomp.over(new SockJS('./ws'));
                    var headers = {};
                    client.connect(headers, function (frame) {
                        self.connected = true;
                        self._subscribes(client);

                        dfd.resolve();
                    }, function () {
                        dfd.reject();
                    });

                    return dfd.promise;
                },

                disconnect: function () {
                    var dfd = $q.defer();
                    var self = this;
                    self._client && self.connected && self._client.disconnect(function () {
                        self.connected = false;
                        dfd.resolve();
                    });
                },

                _subscribes: function (client) {
                    client.subscribe('/topic/greetings', function (greeting) {
                        console.log(greeting);
                    });
                    client.subscribe('/topic/friends/signin', function () {
                        console.log(arguments);
                    });
                    client.subscribe('/topic/friends/signout', function () {
                        console.log(arguments);
                    });
                },

                sendName: function (name) {
                    if (!this.connected) {
                        return;
                    }

                    this._client.send('/app/hello', {}, angular.toJson({name: name}));
                }
            };
        });
    return moduleName;
});
