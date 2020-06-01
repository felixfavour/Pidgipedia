package com.felixfavour.pidgipedia.util

import com.felixfavour.pidgipedia.entity.Comment
import com.felixfavour.pidgipedia.entity.Eventstamp
import com.felixfavour.pidgipedia.entity.User
import com.felixfavour.pidgipedia.entity.Word

object MockData {
    val user = User(0, "Favour", "Felix", "felixfavour0@gmail.com", 787397983, 1, "Lagos, Nigeria",
        "The quick brown fo fresh diee and you all really know it",
        arrayListOf(), arrayListOf())
    val word =
        Word(name = "pata pata", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        )
    val comment = Comment(
        commentContent = "I am a very really fine guy and you all know that", author = user,
        dateCreated = 8789498849
    )
    val allWords = arrayListOf<Word>(
        Word(name = "pata pata", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "wetin", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shebi", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "abi", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "para", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shele", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "don", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "OG", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "danfo", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "pikin", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "pata", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "agidi", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shebi", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "baddo", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shekpe", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "ajebutter", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "popsy", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "ole", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "chinko", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "ombie", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        )

    )

    val comments = arrayListOf(comment,
        Comment("This is another comment, looks good Huh?", user, 78474874897),
        Comment("This is another another comment, looks great too Huh?", user, 78474874897),
        Comment("This is another another another comment, looks sooo great too Huh?", user, 78474874897)
        )

    val eventStamps = arrayListOf<Eventstamp>(
        Eventstamp(eventTime = 1590655865258, humanEntity = user, isCommentResponse = true, word = allWords[0], comments = comments),
        Eventstamp(eventTime = 1567033200000, rankRewardType = 1, humanEntity = user, word = allWords[9], comments = comments),
        Eventstamp(eventTime = 1590696154714, humanEntity = user, isCommentResponse = true, word = allWords[4], comments = comments),
        Eventstamp(eventTime = 1590655865258, isWordComment = true, humanEntity = user, word = allWords[12], comments = comments),
        Eventstamp(eventTime = 1574463600000, badgeRewardType = 2, humanEntity = user, word = allWords[6], comments = comments),
        Eventstamp(eventTime = 1590690000000, isApproved = true, humanEntity = user, word = allWords[16], comments = comments)
    )
    val words = arrayListOf<Word>(
        Word(name = "pata pata", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "wetin", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shebi", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "abi", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "para", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "shele", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "don", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "verb"
        ),
        Word(name = "OG", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "danfo", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        ),
        Word(name = "pikin", meaning = "This means you are completely done", comments = arrayListOf(), englishEquivalent = "Overall",
            sentences = arrayListOf("Pata pata we don finish everything", "Im comot the guy head pata pata"), isApproved = true, authorId = user.userID, wordId = 5, 
            etymology = "Originated from the word pata meaning pynt", dateCreated = 984993793, lastUpdated = 87889897978, plural = null, syllabicDivision = "pa-ta-pa-ta",
            syllables = 4, synonyms = arrayListOf("love", "peace", "joy"), transcription = "/pætapæta/", wordOfTheDay_date = 88783979378, partOfSpeech = "noun"
        )
    )
}