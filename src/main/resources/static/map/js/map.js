var MapSearch = {
    recentlyParam: {},
    coords: undefined,
    searchBy: function (param, callback) {
        if (!param.query) {
            alert('검색어를 입력해주세요.');
            return;
        }

        if (MapSearch.coords) {
            param.x = MapSearch.coords.x;
            param.y = MapSearch.coords.y;
        }

        MapSearch.recentlyParam = param;

        $.ajax({
            method: 'GET',
            url: '/api/map/search',
            data: param
        }).done(function (data) {
            if (callback) {
                callback(data);
            }
        }).fail(function (xhr) {
            var response = xhr.responseJSON;
            var errors = response.errors;
            if (errors) {
                alert(errors[0].defaultMessage);
            } else {
                alert('장소 검색에 실패했습니다.');
            }
        });
    },
    param: function (keyword, page, size) {
        this.query = keyword;
        this.page = page || 1;
        this.size = size || 15;
    },
    renderList: function (searchResult) {

        var listWarpper = document.getElementById('searchResultList');
        $('.search-result-box').show();

        if ((!searchResult.totalCount) || (!searchResult.resultCount)) {
            var noResultElem = document.createElement('div');
            noResultElem.innerText = '검색된 결과가 없습니다.';
            noResultElem.classList.add('no-search-result');

            listWarpper.appendChild(noResultElem);
            return;
        }


        for (var i in searchResult.infomation) {
            var infomation = searchResult.infomation[i];

            var itemElem = document.createElement('div');
            itemElem.classList.add('item');

            var iconElem = document.createElement('i');
            iconElem.classList.add('map', 'marker', 'icon');

            itemElem.appendChild(iconElem);

            var contentElem = document.createElement('div');
            contentElem.classList.add('content');

            var titleElem = document.createElement('a');
            titleElem.classList.add('header', 'place-link');
            titleElem.setAttribute('href', infomation.placeUrl);
            titleElem.setAttribute('target', '_blank');
            titleElem.innerText = infomation.placeName;

            var addressElem = document.createElement('div');
            addressElem.classList.add('description');
            if (infomation.roadAddressName) {
                addressElem.innerHTML = '<a target="_blank" href="' + infomation.mapUrl + '">' + infomation.roadAddressName + ' (지번 주소 : ' + infomation.addressName + ')</a>';
            } else {
                addressElem.innerHTML = '<a target="_blank" href="' + infomation.mapUrl + '">' + infomation.addressName + '</a>';
            }

            var phoneElem = document.createElement('div');
            phoneElem.classList.add('description');
            phoneElem.innerHTML = '<a href="tel:' + infomation.phone + '">' + infomation.phone + '</a>';

            contentElem.appendChild(titleElem);
            contentElem.appendChild(addressElem);
            contentElem.appendChild(phoneElem);

            itemElem.appendChild(contentElem);

            listWarpper.appendChild(itemElem);
        }

        var viewMoreElemId = 'viewMore';
        var viewMoreElem = document.getElementById(viewMoreElemId);
        if (viewMoreElem) {
            document.getElementById(viewMoreElemId).remove();
        }

        if (!searchResult.end) {
            viewMoreElem = document.createElement('div');

            viewMoreElem.removeEventListener('click', MapSearch.viewMoreFuction, false);
            viewMoreElem.addEventListener('click', MapSearch.viewMoreFuction);
            viewMoreElem.setAttribute('id', viewMoreElemId);
            viewMoreElem.classList.add('view-more');
            viewMoreElem.innerText = '더보기';

            listWarpper.appendChild(viewMoreElem);
        }
    },
    viewMoreFuction: function () {
        if (!MapSearch.recentlyParam) {
            return;
        }
        var recentlyParam = MapSearch.recentlyParam;
        recentlyParam.page = recentlyParam.page + 1;
        MapSearch.searchBy(recentlyParam, MapSearch.renderList)
    },
    getKeywordHistory: function (callback) {
        $.ajax({
            method: 'GET',
            url: '/api/map/search/keyword/history',
            data: {
                size: 5
            }
        }).done(function (data) {
            if (callback) {
                callback(data);
            }
        }).fail(function (xhr) {
            var response = xhr.responseJSON;
            console.error(response)
        })
    },
    addRecentKeyword: function (keyword, init) {
        var $titleElem = $('#recentlyKeywordTitle');
        var $wrapperElem = $titleElem.parent();
        var $sameKeywordElem = $wrapperElem.children('.search-keyword[data-keyword="' + keyword + '"]');

        if ($sameKeywordElem.length !== 0) {
            $titleElem.after($sameKeywordElem);
            return;
        }

        var $searchKeywordList = $wrapperElem.children('.search-keyword');
        var $keywordElem = $('<a class="detail search-keyword" data-keyword="' + keyword + '">' + keyword + '</a>');

        if (init) {
            $wrapperElem.append($keywordElem);
            return;
        }

        if ($searchKeywordList.length === 5) {
            $searchKeywordList[4].remove();
        }

        $titleElem.after($keywordElem);
    }
};

$(document).ready(function () {

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            MapSearch.coords = {
                x: position.coords.longitude,
                y: position.coords.latitude
            };
        });
    }

    $('#searchBox').on('keypress', function (evt) {
        if (evt.key === 'Enter') {
            var $searchBox = $('#searchBox');
            if (!$searchBox.val()) {
                return;
            }
            $('#searchResultList').empty();
            var keyword = $searchBox.val();
            MapSearch.searchBy(new MapSearch.param(keyword), MapSearch.renderList);
            MapSearch.addRecentKeyword(keyword);
        }
    });

    $(document).on('click', '.search-keyword', function () {
        $('#searchResultList').empty();
        var keyword = $(this).data('keyword');
        MapSearch.searchBy(new MapSearch.param(keyword), MapSearch.renderList);
        MapSearch.addRecentKeyword(keyword);
    });

    MapSearch.getKeywordHistory(function (data) {
        for (var i in data) {
            var keyword = data[i].keyword;
            MapSearch.addRecentKeyword(keyword, true);
        }
    });
});