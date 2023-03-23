import org.junit.Assert.assertTrue
import org.junit.Test

class UnitTest {
    @Test
    fun test1(){
        heroes.clear()
        monarchHero = CaoCao()
        heroes.add(monarchHero)
//        for(i in 0..7){
//            heroes.add(NoneMonarchFactory.createRandomHero())
//        }
        assertTrue(monarchHero.name == "Cao Cao")
    }

    @Test
    fun test2(){
//        heroes.clear()
        if(heroes.size==0){
            heroes.add(NoneMonarchFactory.createRandomHero())
        }
        val hero = ZhangFei(MinisterRole())
        val spy = object: WarriorHero(MinisterRole()) {
            override val name = hero.name
            override fun beingAttacked() {
                hero.beingAttacked()
                assertTrue(hero.hp >= 0)
            }
        }
        for(i in 0..10)
            spy.beingAttacked()
    }

    object FakeFactory: GameObjectFactory {
        var count = 0
        var last: WeiHero? = null
        init {
            monarchHero = CaoCao()
        }
        override fun getRandomRole(): Role =
            MinisterRole()
        override fun createRandomHero(): Hero {
            val hero = when(count++) {
                0->SimaYi(getRandomRole())
                1->XiaHouyuan (getRandomRole())
                else->XuChu(getRandomRole())
            }
            val cao = monarchHero as CaoCao
            if (last == null)
                cao.helper = hero
            else
                last!!.setNext(hero)
            last = hero
            return hero
        }
    }

    @Test
    fun test3() {
        test1()
        var weihero = FakeFactory.createRandomHero()
        heroes.add(weihero)
        (heroes[0] as CaoCao).sethelper(weihero as Handler)
        heroes[0].beingAttacked()
        for(i in heroes){
            println(i.name)
        }
    }

    class DummyRole : Role {
        override val roleTitle = "Dummy"
        override fun getEnemy() = "Not need"
    }
    @Test
    fun test4() {
        val dummy = DummyRole()
        val hero = ZhangFei(dummy)
        hero.drawCards()
        hero.discardCards()
    }
}