# App Filmes
App que exibe Filmes através da [The Movie Database API](https://www.themoviedb.org/documentation/api).
<br>O app utiliza a biblioteca [Retrofit](https://square.github.io/retrofit/) para fazer requisições para a API, onde são feitas requisições [GET POPULAR](https://developers.themoviedb.org/3/movies/get-popular-movies) para exibir os filmes populares, [GET IMAGES](https://developers.themoviedb.org/3/movies/get-movie-images) 
para exibir as imagens de poster dos filmes, [GET DETAILS](https://developers.themoviedb.org/3/movies/get-movie-details) para exibir os detalhes, além do título e avalição de cada filme.
<br>São utilizados conceitos da arquitetura MVVM onde são implementados: [Live Data](https://developer.android.com/topic/libraries/architecture/livedata?hl=pt-br), Obervables, 
[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAiAvriMBhAuEiwA8Cs5ldjXr9hFgvwI9FN15ElL1tCkDfVf_VImdv6ifu5No4krUqR_J2-ochoC-0gQAvD_BwE&gclsrc=aw.ds), Repository e Client Structure.
<br>Multiplas Views aplicadas em RecyclerView e paginação.
<br>Passagem de dados entre activities usando Intent. Executors, Backgroud Threads e ThreadPools.
<br>Para fazer a exibição dos posters utiliza-se a biblioteca [Glide](https://github.com/bumptech/glide).
<br>É possível ainda fazer a busca por filmes na API através do Search View nativo.

<br>![exemplo](https://media1.giphy.com/media/ATUmVRsVIm5l1lwCZY/giphy.gif?cid=790b7611ae5c75cc2cd28d5a18a77422a2b9130a99853cfd&rid=giphy.gif&ct=g)
