<template>
  <div id = "pick-me-container">
    <div id = "search-bar">
        <div id = "inputs">
            <div class = "data-container">
                <div class = "data-name"> 
                    Flight number
                </div>
                <div class = "data-input"> 
                    <input id = "flight-nr" type = "text" class = "input"/>
                </div>

            </div>
            <div class = "data-container">
                <div class = "data-name"> 
                    Date
                </div>
                <div class = "data-input"> 
                    <input id = "date" type = "text" class = "input">
                </div>

            </div>
                    <div class = "data-container">
                <div class = "data-name"> 
                    Localization
                </div>
                <div class = "data-input"> 
                    <input id = "localization" type="text" class = "input">
                </div>

            </div>
                    <div class = "data-container">
                <div class = "data-name"> 
                    Telephone number
                </div>
                <div class = "data-input"> 
                    <input id = "tel-nr" type="text" class = "input">
                </div>

            </div>
        </div>
        <div class = "search-button">
            Search
        </div>
    </div>

<!--       <div id = "map">
        <img src = "../assets/map.png" />
      </div> -->
  </div>
</template>

<script>

export default {
  name: 'PickMe'
  
}
</script>

<style scoped>
    #search-bar{
       background-image:  linear-gradient(to top,#1e8adf,#2994e6);
       border-bottom-left-radius: 5px;
       border-bottom-right-radius: 5px;
       border-bottom-color:rgb(17, 119, 202);
       border-bottom-style: solid;
       border-bottom-width: 2px;
       border-left-style: solid;
       border-left-width: 2px;
       border-left-color: rgb(17, 119, 202);
       border-right-style: solid;
       border-right-width: 2px;
       border-right-color: rgb(17, 119, 202);
       border-top-left-radius: 5px;
       border-top-right-radius: 5px;
       border-top-color:rgb(17, 119, 202);
       border-top-style: solid;
       border-top-width:2px;
       margin: auto;
       margin-bottom: 5px;
       width: 80%;
    }
    .data-name{
        background-color: rgb(238, 245, 250);
       color: rgb(46, 46, 46);

    }
    .input{
        width:100%;
    }
    .inputs{
        margin: 10px;
    }
    .data-container{
       display: inline;
       margin: 5px;       
       float: left;
       width: 23%;
       border-top-left-radius: 3.23px;
       border-top-right-radius: 3.23px;
       border-top-style: solid;
       border-top-width:2px;
       box-sizing: border-box;
       border-top-color:rgb(238, 245, 250);;
    }
    .search-button{
        background-color: rgb(241, 201, 51);
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
        border-bottom-color:rgb(241, 201, 51);
        border-bottom-style: solid;
        border-bottom-width: 2px;
        border-left-style: solid;
        border-left-width: 2px;
        border-left-color: rgb(241, 201, 51);
        border-right-style: solid;
        border-right-width: 2px;
        border-right-color: rgb(241, 201, 51);
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        border-top-color:rgb(241, 201, 51);
        border-top-style: solid;
        border-top-width:2px;
        width: 25%;
        margin: 5px;
        color: rgb(7, 53, 144);
        display: inline-block;
    }
</style>
