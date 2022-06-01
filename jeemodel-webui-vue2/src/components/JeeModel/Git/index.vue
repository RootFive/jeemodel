<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'JeeModelGit',
  data() {
    return {
      url: 'https://gitee.com/jeemodel/jeemodel'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>