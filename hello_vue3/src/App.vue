<template>
    <div class="app">
        <h1>Hello Vue!</h1>
        <Person></Person>
    </div>
</template>

<script>
    import Person from './components/Person.vue';

    export default {
        name: 'App',
        components: {
            Person
        }
    }
</script>

<style>
    .app{
        background-color:red;
        box-shadow: 0 0 10px;
        border-radius: 10px;
        padding: 20px;
    }
</style>