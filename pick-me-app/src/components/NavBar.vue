<template>
  <div id="ryanair-navbar">
        <navbar class = "navbar blue-navbar">
            <navbar-nav>
                <nav-item class = "nav-item nav-link"  >
                    <a class = "nav-link" href = 'https://www.ryanair.com/'>
                    <img src = "../assets/ryanair-blue-logo.png" class = "nav-img"/> 
                    </a>
                 </nav-item>  
                <span class = "navbar-separator">
                </span>
                <nav-item class="nav-item">
                    Plan
                </nav-item>  
                <span class = "navbar-separator">
                </span>  
                <nav-item class="nav-item">
                    My bookings
                </nav-item> 
                <span class = "navbar-separator">
                </span>
                <nav-item class="nav-item">
                    Check-in
                </nav-item> 
                <span class = "navbar-separator">
                </span>
                <nav-item class="nav-item">
                    Holidays
                </nav-item>
                <span class = "navbar-separator">
                </span> 
                <nav-item class="nav-item">
                    Car-hire
                </nav-item>      
            </navbar-nav>

        </navbar>
    
    
  </div>
</template>

<script>
export default {
  name: 'RyanairNavbar'
  
}
</script>

<style scoped>

    .blue-navbar{
        background-color:rgba(7,53,144,1.00);
        height: 3.2em;
        border-bottom: 2px solid #F1C933;
        margin-bottom: 5px;
    }
    .nav-container{
        position: fixed;
        top: 0;
    }
    .nav-item{
        color:#fff;
        display: inline-flex;

    }
    .nav-item:hover{
       color:#F1C933; 
    }
    .navbar-separator{
        border-right: 1px solid #204A9C;
        position: relative;
        height: 22px;
        width: 1px;
        display: inline-block;
        top: 6px;
    }
    .nav-img{
        height: 1.3em;
    }
</style>
