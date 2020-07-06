package com.felixfavour.pidgipedia.util

import com.felixfavour.pidgipedia.entity.*

object MockData {
    val user2 = RemoteUser(
        "jkjdkjd",
        "Chinemerem",
        "Kenechukwu",
        "felixfavour0@gmail.com",
        1590655865258,
        1,
        "Lagos, Nigeria",
        "The quick brown fo fresh diee and you all really know it",
        listOf(Badges.BADGE_1, Badges.BADGE_2, Badges.BADGE_5, Badges.BADGE_10, Badges.BADGE_20, Badges.BADGE_25, Badges.BADGE_50),
        arrayListOf(),
        arrayListOf(),
        8,
        "",
        "chidexy"
    )
    val user1 = RemoteUser(
        "jskskhjsjhk",
        "Felix",
        "Favour",
        "felixfavour0@gmail.com",
        787397983,
        1,
        "Lagos, Nigeria",
        "The quick brown fo fresh diee and you all really know it",
        listOf(Badges.BADGE_1, Badges.BADGE_2, Badges.BADGE_5, Badges.BADGE_10, Badges.BADGE_20),
        arrayListOf(),
        arrayListOf(),
        8,
        "",
        "favoob"
    )
    val word =
        Word(name = "pata pata", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "bjjksjkhs",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        )
    val comment = Comment(
        commentContent = "I am a very really fine guy and you all know that", authorId = "user1",
        dateCreated = 8789498849
    )
    val allWords = arrayListOf<Word>(
        Word(name = "pata pata", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "wetin", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shebi", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "abi", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "para", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shele", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "don", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "OG", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "danfo", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "pikin", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "pata", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "agidi", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shebi", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "baddo", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shekpe", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "ajebutter", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "popsy", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "ole", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "chinko", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "ombie", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        )

    )

    val comments = arrayListOf(comment,
        Comment("","This is another comment, looks good Huh?", "", "",78474874897),
        Comment("","This is another another comment, looks great too Huh?", "", "", 78474874897),
        Comment("","This is another comment, looks good Huh?", "",  "",78474874897),
        Comment("","This is another another comment, looks great too Huh?", "", "", 784874897),
        Comment("","This is another comment, looks good Huh?", "", "", 784748747),
        Comment("","This is another another comment, looks great too Huh?",  "","", 7844874897),
        Comment("","This is another comment, looks good Huh?", "",  "",7847487487),
        Comment("","This is another another comment, looks great too Huh?", "",  "", 7844874897),
        Comment("","This is another another another comment, looks sooo great too Huh?", "", "", 78474874897)
        )

    val eventStamps = listOf<Eventstamp>(
        Eventstamp(eventTime = 1590655865258, humanEntityId = "jdjkhkhjjkjhjk", commentResponse = true, wordId = ""),
        Eventstamp(eventTime = 1567033200000, rankRewardType = 1, humanEntityId = "",wordId = ""),
        Eventstamp(eventTime = 1590696154714, humanEntityId = "", commentResponse = true, wordId = ""),
        Eventstamp(eventTime = 1590655865258, wordComment = true, humanEntityId = "", wordId = "allWords"),
        Eventstamp(eventTime = 1574463600000, badgeRewardType = Badges.BADGE_20, humanEntityId = "", wordId = "allWords"),
        Eventstamp(eventTime = 1590690000000, approved = true, humanEntityId = "", wordId = "")
    )

    val words = arrayListOf<Word>(
        Word(name = "pata pata", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "wetin", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shebi", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "abi", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "para", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shele", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "don", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "OG", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "danfo", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "pikin", meaning = "This means you are completely done", englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), approved = true, authorId = user1.userId!!, wordId = "jkdkjhd",
            etymology = "Originated from the wordId pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        )
    )

    val quizzes = listOf<Quiz>(
        Quiz(0, "How long is iroko tree?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(1, "How long is iroko tree\'s father?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(2, "What is the fastest car in the world?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(3, "How much people do you know in the world?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(4, "Do you think you are really smart?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(5, "How many bottles of beer can you drink in 30 seconds?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(6, "Would you ever buy a tesla, If you could afford it?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(7, "How much are you willing to offer to the richest man in the World?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz( 8, "Is ronaldo or messi the best player in the football universe?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true),
        Quiz(9, "How much do I pay you to be able to afford all of this?", listOf("50m", "100m", "3m", "25m"),  1, 7882627379837893, 27, true)
    )
}