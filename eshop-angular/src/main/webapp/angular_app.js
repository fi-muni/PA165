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
        when('/category/:categoryId', {templateUrl: 'partials/category_detail.html', controller: 'CategoryDetailCtrl'}).
        when('/admin/products', {templateUrl: 'partials/admin_products.html', controller: 'AdminProductsCtrl'}).
        when('/admin/newproduct', {templateUrl: 'partials/admin_new_product.html', controller: 'AdminNewProductCtrl'}).
        when('/admin/categories', {templateUrl: 'partials/admin_categories.html', controller: 'AdminCategoriesCtrl'}).
        when('/admin/newcategory', {
            templateUrl: 'partials/admin_new_category.html',
            controller: 'AdminNewCategoryCtrl'
        }).
        otherwise({redirectTo: '/shopping'});
    }]);

/*
 * alert closing functions defined in root scope to be available in every template
 */
pa165eshopApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});


/* Controllers */



/*
 * Public eshop interface
 */

// helper procedure loading products to category
function loadCategoryProducts($http, category, prodLink) {
    $http.get(prodLink).then(function (response) {
        category.products = response.data['_embedded']['products'];
        console.log('AJAX loaded ' + category.products.length + ' products to category ' + category.name);
    });
}

/*
 * Shopping page with all categories and products
 */
eshopControllers.controller('ShoppingCtrl', function ($scope, $http) {
    console.log('calling  /eshop/api/v1/categories/');
    $http.get('/eshop/api/v1/categories/').then(function (response) {
        var categories = response.data['_embedded']['categories'];
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

/*
 * Category detail page
 */

eshopControllers.controller('CategoryDetailCtrl', ['$scope', '$routeParams', '$http',
    function ($scope, $routeParams, $http) {
        var categoryId = $routeParams.categoryId;
        $http.get('/eshop/api/v1/categories/' + categoryId).then(function (response) {
            var category = response.data;
            $scope.category = category;
            console.log('AJAX loaded detail of category ' + category.name);
            loadCategoryProducts($http, category, category['_links'].products.href);
        });
    }]);

/*
 * Administration interface
 */

/*
 * Admin Products page
 */
function loadAdminProducts($http, $scope) {
    $http.get('/eshop/api/v1/products').then(function (response) {
        $scope.products = response.data._embedded.products;
        console.log('AJAX loaded all products ');
    });
}
eshopControllers.controller('AdminProductsCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        //initial load of all products
        loadAdminProducts($http, $scope);
        // function called when Delete button is clicked
        $scope.deleteProduct = function (product) {
            console.log("deleting product with id=" + product.id + ' (' + product.name + ')');
            $http.delete(product._links.delete.href).then(
                function success(response) {
                    console.log('deleted product ' + product.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted product "' + product.name + '"';
                    //load new list of all products
                    loadAdminProducts($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting product");
                    console.log(response);
                    switch (response.data.code) {
                        case 'ResourceNotFoundException':
                            $rootScope.errorAlert = 'Cannot delete non-existent product ! ';
                            break;
                        default:
                            $rootScope.errorAlert = 'Cannot delete product ! Reason given by the server: '+response.data.message;
                            break;
                    }
                }
            );
        };
    });


/*
 * Page with form for creating new product
 */
eshopControllers.controller('AdminNewProductCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //prepare data for selection lists
        $scope.colors = ['RED', 'GREEN', 'BLUE', 'BLACK'];
        $scope.currencies = ['CZK', 'EUR', 'USD'];
        //get categories from server
        $http.get('/eshop/api/v1/categories/').then(function (response) {
            $scope.categories = response.data['_embedded']['categories'];
        });
        //set object bound to form fields
        $scope.product = {
            'name': '',
            'description': '',
            'categoryId': 1,
            'price': 0,
            'color': $scope.colors[1],
            'currency': $scope.currencies[0]
        };
        // function called when submit button is clicked, creates product on server
        $scope.create = function (product) {
            $http({
                method: 'POST',
                url: '/eshop/api/v1/products/create',
                data: product
            }).then(function success(response) {
                console.log('created product');
                var createdProduct = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'A new product "' + createdProduct.name + '" was created';
                //change view to list of products
                $location.path("/admin/products");
            }, function error(response) {
                //display error
                console.log("error when creating product");
                console.log(response);
                switch (response.data.code) {
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create product ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });

// defines new directive (HTML attribute "convert-to-int") for conversion between string and int
// of the value of a selection list in a form
// without this, the value of the selected option is always a string, not an integer
eshopControllers.directive('convertToInt', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                return '' + val;
            });
        }
    };
});

/*
 * Admin categories
 */
eshopControllers.controller('AdminCategoriesCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        //initial load of all categories
        $http.get('/eshop/api/v1/categories').then(function (response) {
            $scope.categories = response.data._embedded.categories;
        });
    });

/*
 * Page with form for creating new category
 */
eshopControllers.controller('AdminNewCategoryCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //set object bound to form fields
        $scope.category = {
            'name': ''
        };
        // function called when submit button is clicked, creates category on server
        $scope.create = function (category) {
            $http({
                method: 'POST',
                url: '/eshop/api/v1/categories/create',
                data: category
            }).then(function success(response) {
                var createdCategory = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'A new category "' + createdCategory.name + '" was created';
                //change view to list of products
                $location.path("/admin/categories");
            }, function error(response) {
                //display error
                console.log("error when creating category");
                console.log(response);
                switch (response.data.code) {
                    case 'PersistenceException':
                        $rootScope.errorAlert = 'Category with the same name already exists ! ';
                        break;
                    case 'InvalidRequestException':
                        $rootScope.errorAlert = 'Sent data were found to be invalid by server ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot create category ! Reason given by the server: '+response.data.message;
                        break;
                }
            });
        };
    });