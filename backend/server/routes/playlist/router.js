"use strict";

let express = require('express');
let router = express.Router();
let manager = require('./manager');
let http = require('http');
let request = require('request');

//해당 태그의 playlist 가져오기 
router.route('/playlist').get(function (req, res) {
    let tag = req.query.tag;

    manager.getPlaylist(tag, function (response) {
         console.log(response);
        if (!!response.music) {
            res.writeHead(200, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(204, {
                'Content-Type': 'application/json'
            });
        }
        
        res.write(JSON.stringify(response));
        res.end();
    });
});

//현재 날씨 가져오기
router.route('/weather').get(function (req, res) {
    let x = req.query.x;
    let y = req.query.y;
    let response = {
        weather: null,
        temperature: null
    };

    let weather = requestAPI(x, y, function (weather, temperature) {
        response.weather = weather;
        response.temperature=temperature;
        if (!!response.weather) {
            res.writeHead(200, {
                'Content-Type': 'application/json'
            });
        } else {
            res.writeHead(204, {
                'Content-Type': 'application/json'
            });
        }

        res.write(JSON.stringify(response));
        res.end();
    });
});

function requestAPI(x, y, callback) {
    request('http://apis.skplanetx.com/weather/current/minutely?lon=' + x + '&lat=' + y + '&version=1&appKey=e9c74732-4fa4-374e-bc26-2f352d74e2fd', function (error, response, body) {
        let weather = JSON.parse(body).weather.minutely[0].sky.name;
        let temperature = JSON.parse(body).weather.minutely[0].temperature.tc;
        callback(weather,temperature);
    });
}

module.exports = router;