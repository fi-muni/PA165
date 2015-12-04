'use strict';

/* Defines application and its dependencies */

var pa165eshopApp = angular.module('pa165eshopApp', ['ngRoute', 'eshopControllers']);
var eshopControllers = angular.module('eshopControllers', []);

/* Configures URL fragment routing, e.g. #/product/1  */
pa165eshopApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/shopping', {templateUrl: 'partials/shopping.html', controller: 'ShoppingCtrl'}).
        when('/product/:productId', {templateUrl: 'partials/product_detail.html', controller: 'ProductDetailCtrl'}).
        otherwise({redirectTo: '/shopping'});
    }]);

pa165eshopApp.run(function ($rootScope,$http) {
    // alert closing functions defined in root scope to be available in every template
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
    //change the HTTP Accept header globally to signal accepting the HAL format
    $http.defaults.headers.common.Accept = 'application/hal+json, */*';
});

/* Controllers */



/*
 * Public eshop interface
 */

// helper procedure loading products to category
function loadCategoryProducts($http, category, prodLink) {
    $http.get(prodLink).then(function (response) {
        category.products = response.data['_embedded']['productDTOList'];
        console.log('AJAX loaded ' + category.products.length + ' products to category ' + category.name);
    });
}

/*
 * Shopping page with all categories and products
 */
eshopControllers.controller('ShoppingCtrl', function ($scope, $http) {
    console.log('calling  /eshop/api/v1/categories/');
    $http.get('/eshop/api/v1/categories/').then(function (response) {
        var categories = response.data['_embedded']['categoryDTOList'];
        console.log('AJAX loaded all categories');
        $scope.categories = categories;
        for (var i = 0; i < categories.length; i++) {
            var category = categories[i];
            var categoryProductsLink = category['_links'].products.href;
            loadCategoryProducts($http, category, categoryProductsLink);
        }
    });
});


/*
 * Product detail page
 */
eshopControllers.controller('ProductDetailCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        // get product id from URL fragment #/product/:productId
        var productId = $routeParams.productId;
        $http.get('/eshop/api/v1/products/' + productId).then(
            function (response) {
                $scope.product = response.data;
                console.log('AJAX loaded detail of product ' + $scope.product.name);
            },
            function error(response) {
                console.log("failed to load product "+productId);
                console.log(response);
                $rootScope.warningAlert = 'Cannot load product: '+response.data.message;
            }
        );
    });

