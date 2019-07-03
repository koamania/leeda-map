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

            var contentElem = document.createElement('div');
            contentElem.classList.add('content');

            var titleElem = document.createElement('span');
            titleElem.classList.add('header', 'place-name');
            titleElem.innerText = infomation.placeName;

            var placeLinkElem = document.createElement('a');
            placeLinkElem.setAttribute('href', infomation.placeUrl);
            placeLinkElem.setAttribute('target', '_blank');
            placeLinkElem.innerHTML = '<i class="external alternate icon"></i>';

            var addressElem = document.createElement('div');
            addressElem.classList.add('description');
            if (infomation.roadAddressName) {
                addressElem.innerHTML = infomation.roadAddressName + ' (지번 주소 : ' + infomation.addressName + ')';
            } else {
                addressElem.innerHTML = infomation.addressName;
            }
            addressElem.innerHTML = addressElem.innerHTML + '<a target="_blank" href="' + infomation.mapUrl + '"><i class="external alternate icon"></i></a>';

            var phoneElem = document.createElement('div');
            phoneElem.classList.add('description');
            phoneElem.innerHTML = infomation.phone;

            contentElem.appendChild(titleElem);
            contentElem.appendChild(placeLinkElem);
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
    addRecentKeyword: function (keyword, date) {
        var dateString = date.format('yyyy-MM-dd(KS) HH:mm:ss');
        $('#historyItemWrapper').append(
            '<tr><td><a href="javascript:void(0);" class="search-keyword modal-hide" data-keyword="' + keyword + '">' + keyword + '</a></td>' +
            '<td>' + dateString + '</td></tr>'
        )
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
        }
    });

    $(document).on('click', '.search-keyword', function () {
        $('#searchResultList').empty();
        var keyword = $(this).data('keyword');
        $('#searchBox').val(keyword);
        MapSearch.searchBy(new MapSearch.param(keyword), MapSearch.renderList);
    });

    $(document).on('click', '.modal-hide', function () {
        $('.ui.modal').modal('hide');
    });

    $('#keywordHistoryBtn').on('click', function () {
        KeywordHistory.getKeywordHistory({page: 0, size: 15}, function (data) {

            var isEmpty = data.empty;
            if (!isEmpty) {
                $('#historyItemWrapper').empty();
                var keywordList = data.content;
                KeywordHistory.renderHistory(keywordList, data.last);
            }

            $('#searchHistory').modal('show');
        });
    });

    $('#keywordRankBtn').on('click', function () {
        KeywordRank.getKeywordRank(KeywordRank.resultCount, KeywordRank.renderKeywordRank);
        $('#keywordRank').modal('show');
    });
});

var KeywordHistory = {
    recentlyParam: {
        size: 15,
        page: 0
    },
    renderHistory: function (keywordList, last) {
        for (var i in keywordList) {
            var keywordInfo = keywordList[i];
            var keyword = keywordInfo.keyword.keyword;
            var date = keywordInfo.createdDate;
            MapSearch.addRecentKeyword(keyword, new Date(date));
        }


        var viewMoreElemId = 'viewMoreHistory';
        var viewMoreElem = $('#' + viewMoreElemId);
        if (viewMoreElem.length !== 0) {
            viewMoreElem.remove();
        }

        if (!last) {
            var newViewMoreElem = $('<tr id="viewMoreHistory"><td colspan="2" class="colspanText">더보기</td></tr>');
            // KeywordHistory.renderHistoryTable
            // KeywordHistory.pagingParam
            newViewMoreElem.on('click', function () {
                var param = KeywordHistory.recentlyParam;
                param.page = param.page + 1;

                KeywordHistory.getKeywordHistory(param, function (data) {
                    var elementCount = data.numberOfElements;
                    if (!elementCount) {
                        return;
                    }

                    var keywordList = data.content;
                    KeywordHistory.renderHistory(keywordList, data.last);
                });
            });
            $('#historyItemWrapper').append(newViewMoreElem);
        }
    },
    getKeywordHistory: function (param, callback) {
        KeywordHistory.recentlyParam = param;

        $.ajax({
            method: 'GET',
            url: '/api/map/search/keyword/history',
            data: param
        }).done(function (data) {
            if (callback) {
                callback(data);
            }
        }).fail(function (xhr) {
            var response = xhr.responseJSON;
            console.error(response)
        })
    }
};
var KeywordRank = {
    resultCount: 10,
    getKeywordRank: function (resultCount, callback) {
        $.ajax({
            method: 'GET',
            url: '/api/map/search/keyword/top',
            data: {
                result_count: resultCount
            }
        }).done(function (data) {
            if (callback) {
                callback(data);
            }
        }).fail(function (xhr) {
            var response = xhr.responseJSON;
            console.error(response)
        });
    },
    renderKeywordRank: function (keywordList) {
        if (!keywordList) {
            return;
        }

        var $wrapper = $('#rankItemWrapper');
        $wrapper.empty();

        for (var i in keywordList) {
            var keywordInfo = keywordList[i];
            var keyword = keywordInfo.keyword;
            var count = keywordInfo.hitCount;
            $wrapper.append(
                '<tr><td><a href="javascript:void(0);" class="search-keyword modal-hide" data-keyword="' + keyword + '">' + keyword + '</a></td>' +
                '<td>' + count + '</td></tr>'
            );
        }
    }
};