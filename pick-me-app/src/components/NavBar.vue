<template>
  <div id="ryanair-navbar">
        <navbar class="navbar blue-navbar">
            <navbar-nav>
                <nav-item class="nav-item" >
                    <img src="../assets/ryanair-blue-logo.png" href = 'https://www.ryanair.com/pl/pl/' />  
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
        height: 4em;
    }
    .nav-container{
        position: fixed;
        top: 0;
    }
    .nav-item{
        color:white;
    }
    .navbar-separator{
        border-right: 1px solid #204A9C;
        position: relative;
        height: 22px;
        width: 1px;
        display: inline-block;
        top: 6px;
    }
</style>
