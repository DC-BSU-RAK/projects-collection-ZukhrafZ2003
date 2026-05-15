package com.example.swiftly

object EraCalculatorEngine {

    private val singleResponses = mapOf(
        "debut" to "You are at the very beginning of something that will change everything.",
        "fearless" to "Something golden is waiting just around the corner. Keep walking.",
        "speak_now" to "The moment is now. Say the thing before the doors close.",
        "red" to "Losing something real is how you know it mattered. It mattered.",
        "nineteen89" to "New city. New name. New you. It worked, didn't it.",
        "reputation" to "Let them underestimate you. You have already planned your next move.",
        "lover" to "You are allowed to be happy. Truly, embarrassingly, fully happy.",
        "folklore" to "Step into the trees. The answers live in the quiet.",
        "evermore" to "You have survived more than you have told anyone. That counts.",
        "midnights" to "The 3am thoughts are the most honest ones. Listen to them.",
        "ttpd" to "You are not too much. You are exactly enough, just for the right reader.",
        "vault" to "The things you kept hidden deserved the light all along."
    )

    private val pairResponses = mapOf(
        setOf("debut", "fearless") to "You grew from wonder into gold. That glow never fully left you.",
        setOf("debut", "red") to "The first love always leaves the deepest mark. You know this.",
        setOf("debut", "midnights") to "Late nights remind you of who you were before the world got complicated.",
        setOf("debut", "ttpd") to "Innocence and raw honesty are not opposites. You are proof.",
        setOf("fearless", "red") to "You loved fearlessly once. The scars proved it was real.",
        setOf("fearless", "lover") to "This is the version of love you always believed existed. You were right.",
        setOf("fearless", "folklore") to "The brave girl and the quiet poet are the same person, just different seasons.",
        setOf("fearless", "midnights") to "Even the fearless ones lie awake sometimes.",
        setOf("fearless", "nineteen89") to "You stepped into the sunshine and did not look back. Iconic.",
        setOf("speak_now", "ttpd") to "You showed up and said the thing. Even when your voice shook.",
        setOf("speak_now", "vault") to "The speech you almost never gave - that was the important one.",
        setOf("red", "nineteen89") to "You burned it down and built something brighter. Expensive lesson, good result.",
        setOf("red", "folklore") to "You archived the grief. Now you understand it.",
        setOf("nineteen89", "vault") to "Some chapters were better the second time around.",
        setOf("reputation", "lover") to "After the armour comes the softness. You let someone see both.",
        setOf("reputation", "midnights") to "The darkness you survived became the story you control.",
        setOf("reputation", "folklore") to "Underneath the snake imagery was just someone trying to survive.",
        setOf("reputation", "nineteen89") to "You reinvented yourself once. Then again. You are an expert at this now.",
        setOf("lover", "folklore") to "Even the softest hearts write in pencil sometimes - just in case.",
        setOf("lover", "midnights") to "You think about them at 3am and it does not even embarrass you.",
        setOf("lover", "ttpd") to "Love made you a better writer. It also wrecked you completely.",
        setOf("lover", "evermore") to "Every joy carries a small grief. That is what makes it precious.",
        setOf("folklore", "evermore") to "Two sides of the same coin, tossed in winter. Both landed wisdom.",
        setOf("folklore", "midnights") to "You are a person who feels things deeply and writes them down darker.",
        setOf("folklore", "ttpd") to "The cabin and the confessional are not so different after all.",
        setOf("evermore", "midnights") to "From the winter woods to the 3am ceiling - you carry your seasons with you.",
        setOf("evermore", "ttpd") to "There is a poem in every wound. You found most of them.",
        setOf("midnights", "ttpd") to "You turned insomnia into literature. Honestly, a power move.",
        setOf("midnights", "vault") to "Some of the best thoughts were the ones you almost did not share.",
        setOf("ttpd", "vault") to "The things you almost kept to yourself were the most important ones."
    )

    private val threeEraResponses = mapOf(
        setOf("folklore", "evermore", "midnights") to "Three winters inside your own mind. The poems were worth it.",
        setOf("reputation", "lover", "folklore") to "You rebuilt yourself in armour, then in flowers, then in silence. Every version was true.",
        setOf("nineteen89", "reputation", "lover") to "You escaped, you fought, you softened. The ending was better than the prologue.",
        setOf("debut", "ttpd", "vault") to "From the very first song to the very last secret - you were always a poet.",
        setOf("midnights", "ttpd", "vault") to "Three a.m., raw honesty, and the things you almost kept hidden. This is your whole truth.",
        setOf("fearless", "red", "evermore") to "Gold turned to autumn turned to wisdom. You grew beautifully.",
        setOf("debut", "fearless", "red") to "You loved, you learned, you grieved. That is the whole human experience in three acts."
    )

    private val fallbacks = listOf(
        "The eras have spoken. Something is shifting in your orbit - pay attention.",
        "Taylor would say: you already know the answer. You just needed to see it spelled out.",
        "This is the bridge section of your life. It is supposed to feel unresolved.",
        "Some equations can only be solved by dancing alone in your bedroom.",
        "May your heart remain breakable but never by the same hand twice.",
        "You are in your main-character era. Whatever comes next - lean in.",
        "Not every era combination has a clean answer. Sometimes the feeling is the answer."
    )

    fun calculate(sequence: List<String>): String {
        if (sequence.size == 1) {
            return singleResponses[sequence[0]] ?: fallbacks.random()
        }
        if (sequence.size == 2) {
            return pairResponses[sequence.toSet()] ?: fallbacks.random()
        }
        val seqSet = sequence.toSet()
        threeEraResponses.forEach { (combo, result) ->
            if (combo.all { it in seqSet }) return result
        }
        return fallbacks.random()
    }
}