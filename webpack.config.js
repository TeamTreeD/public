// Zehnder
var webpack = require('webpack');
var path = require('path');
const HtmlWebpackPlugin = require("html-webpack-plugin");

var BUILD_DIR = path.resolve(__dirname, 'target/webapp');

var config = {
    devServer: {
        host: "0.0.0.0",
        port: 8000,
        proxy: [
            {
                // Make sure that we get a redirect if not logged in.
                context: function (pathname, req) {
                    var proxy = false;
                    proxy |= pathname.startsWith("/positions");
                    return proxy;
                },
                target: "http://localhost:8080"
            }
        ]
    },
    devtool: "source-map",
    entry: 'index.tsx',
    externals: {
        lodash: '_',
    },
    output: {
        path: BUILD_DIR,
        filename: 'bundle.[contenthash].js'
    },
    module: {
        rules: [
            { 
                test: /\.tsx?|.jsx?$/, 
                loader: 'babel-loader',
                options: {
                    presets: ['@babel/react', ['@babel/env', {
                        "useBuiltIns": "entry",
                        "corejs": 2
                      }], '@babel/typescript'],
                    plugins: [
                        '@babel/proposal-class-properties'
                    ]
                }
            }, 
            {
                test: /\.css$/,
                use: [
                    { loader: "style-loader" },
                    {
                      loader: "css-loader",
                      options: {
                        esModule: false,
                        url: false
                      }
                    }
                ]
            },
            {
                test: /\.less$/,
                use: [
                    { loader: 'style-loader' },
                    {
                      loader: 'css-loader',
                      options: {
                        esModule: false,
                        url: false
                      }
                    },
                    { loader: 'less-loader' }
                ]
            },
            {
              test: /\.svg$/,
              use: [
                { loader: "babel-loader" },
                { loader: "react-svg-loader" }
              ]
            },
            {
                test: /\.(png|jpg|jpeg|gif)$/,
                use: {
                    loader: "file-loader",
                    options: {
                        name: "images/[name].[ext]"
                    }
                }
            },
            {
                test: /\.properties$/,
                use: [
                    { loader: "json-loader" },
                    { loader: "enhanced-properties-loader" }
                ]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "src/main/ts/public/index.html",
            inject: "body",
            filename: "index.html"
        })
    ],
    resolve: {
        extensions: ['.js', '.jsx', '.tsx', '.css', '.less'],
        modules: [path.resolve(__dirname, "node_modules"), "./src/main/ts/src"]
    }
};

module.exports = config;
