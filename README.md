# OpenPunkApi
BeerFinder using PunkAPI https://punkapi.com/

<a href="https://imgflip.com/gif/3mktvx"><img src="https://i.imgflip.com/3mktvx.gif" title="made at imgflip.com"/></a>


Приложение состоит из трех фрагментов:
![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/readme/1.jpg)

Фрагмент **Category** – в котором можно подобрать пиво под определенный вид еды: выбрав из определённой категории или ввести в поиске блюдо, например, pizza: 
![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/readme/2.jpg)
В данном фрагменте присутствует постраничная подгрузка данных. Также если нажать на выбранное пиво, откроется профиль этого пива: Такой профиль используется во всех фрагментах.
![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/readme/3.png)

С помощью лайка можно добавить пиво в favorite, если заданное пиво уже в favorite, лайк будет гореть.
Добавление в Favorite происходит путем сохранения пива в базу данных. Тут возник вопрос как хранить картинки. Изначально я сохранял картинки во внутреннюю память телефона и в базу данных писал путь до этой картики, а при удалении из favorite удалял эту картинку. Но возникла ситуация, когда мы подгрузили пиво, пропала сеть и пытаемся сохранить данное пиво. Тогда в бд сохраниться пустая картинка и останется такой навсегда. Поэтому я решил сохранять в бд просто url картинки, и при повторном подключении к интернету, картинка подрузится, даже в том случае, если при добавлении не была загружена. Такой вариант, мне кажется более эстетичным, первый вариант я оставил закомментированным. Также API иногда возвращает пиво, у которого image_url= null.
В таком случае я устанавливаю нейтральную картинку:

![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/app/src/main/res/drawable/baltic9.jpg)

Фрагмент **Random** предлагает рандомное пиво. Оно также проверяется на то, находится ли оно в Favorite, и присутствует функция добавления в Favorite.

![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/readme/4.jpg)

Фрагмент **Favorite**:
Присутствует просмотр любимых напитков, и просмотр профиля.
![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/readme/5.jpg)

Удаление происходит свайпом.
![Image alt](https://github.com/Miklesam/OpenPunkApi/blob/master/readme/6.jpg)

**Спасибо за внимание.**
