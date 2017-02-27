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
            Stomp = $window.Stomp; // not exported
            return {
                _client: undefined,

                connected: false,

                connect: function () {
                    var dfd = $q.defer();
                    var self = this;
                    self._client = Stomp.over(new SockJS('./ws'));
                    var headers = {};
                    self._client.connect(headers, function (frame) {
                        self.connected = true;
                        self._client.subscribe('/topic/greetings', function (greeting) {
                            console.log(greeting);
                        });
                        self._client.subscribe('/topic/friends/signin', function () {
                            console.log(arguments);
                        });
                        self._client.subscribe('/topic/friends/signout', function () {
                            console.log(arguments);
                        });

                        dfd.resolve();
                    }, function () {
                        dfd.reject();
                    });

                    return dfd.promise;
                },

                disconnect: function () {
                    var dfd = $q.defer();
                    this._client && this.connected && this._client.disconnect(function () {
                        this.connected = false;
                        dfd.resolve();
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
