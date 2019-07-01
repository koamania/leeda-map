var MapSearch = {
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

        $.ajax({
            method: 'GET',
            url: '/api/map/search',
            data: param
        }).done(function (data) {
            console.log(data);
            if (callback) {
                callback(data);
            }
        }).fail(function (xhr) {
            var response = xhr.responseJSON;
            var errors = response.errors;
            if (errors) {
                alert(errors[0].defaultMessage);
            } else {
                alert('이메일 혹은 패스워드를 확인해주세요.');
            }
        });
    },
    param: function (keyword, page, size) {
        this.query = keyword;
        this.page = page || 1;
        this.size = size || 15;
    },
    renderList: function (searchResult) {

        if (searchResult.totalCount === 0) {
            alert('검색된 결과가 없습니다.');
            return;
        }

        var listWarpper = document.querySelector('#searchResultList');

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
            titleElem.setAttribute('href', infomation.mapUrl);
            titleElem.setAttribute('target', '_blank');
            titleElem.innerText = infomation.placeName;

            var descElem = document.createElement('div');
            descElem.classList.add('description');
            descElem.innerText = infomation.roadAddressName + '(' + infomation.addressName + ')';

            contentElem.appendChild(titleElem);
            contentElem.appendChild(descElem);

            itemElem.appendChild(contentElem);

            listWarpper.appendChild(itemElem);
        }

        $('.search-result-box').show();
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
            MapSearch.searchBy(new MapSearch.param($searchBox.val()), MapSearch.renderList);
        }
    });
});