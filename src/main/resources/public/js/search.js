jQuery(function () {
    console.log("Hello world");

    /**
     * Ajax promise adapter
     * @param {*} options 
     * @returns Promise
     */
    function ajax(options) {
        return new Promise(function (resolve, reject) {
          $.ajax(options).done(resolve).fail(reject);
        });
    }

    /**
     * Make API call.
     * @param {string} url 
     * @param {Object.<string, string>} params
     * params should be {name: seachTerm}
     */
    function getSearchResult(url, params) {
        return ajax({
            url:  url,
            data: params
        });
    }


    let data = getSearchResult("/api/v1/mangas/ajax/search", {name: "Komi"});
    console.log(data);

    /**
     * Update search result and show the #search-result.
     * @param {string} searchStr
     * @param {JQuery<HTMLElement>} resultHoldElement
     */
    async function updateSearchResult(searchStr, resultHoldElement) {

        if (searchStr.length <= 2) {
            return;
        }

        let param = {name: searchStr};
        let matchedMangas = await getSearchResult("/api/v1/mangas/ajax/search", param);

        resultHoldElement.empty();
        
        if (matchedMangas.length <= 0) {
            resultHoldElement.append("<p>Không có kết quả.</p>");
            return;
        }
        for(let manga of matchedMangas) {
            let template = $(".search-entry", "#template").clone(true, true);
            template.find(".image").attr("src", manga.cover);
            template.find(".title").text(manga.name);
            template.find(".description").text(manga.description);
            template.removeClass("hidden");
            template.attr("href", `/manga/${manga.id}`);
            resultHoldElement.append(template);
            console.log(template.first());
        }
    }



    $("#search-field").on("input", function () {
        $("#search-result").removeClass("hidden");

        updateSearchResult($(this).val(), $("#search-result"));
    });

    $("#search-field").on("focusout", function () {
        setTimeout(() => {
            $("#search-result").addClass("hidden");
            $(this).val("");
        }, 100);
    });

});