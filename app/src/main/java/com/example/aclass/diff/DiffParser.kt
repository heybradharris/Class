            var firstCommitLines: CharSequence = "\n"
            var secondCommitLines: CharSequence = "\n"
                        items.add(
                            DiffRecyclerItem.SectionTwoFiles(
                                lineNumberRange,
                                firstCommitLines,
                                secondCommitLines
                        )
                        firstCommitLines = "\n"
                        secondCommitLines = "\n"
                        firstCommitLines = "\n"
                        secondCommitLines = "\n"
                    var isRed: Boolean
                    var isGreen: Boolean

                    val span: SpannableString = when {
                        line.startsWith("+") -> {
                            val str = SpannableString(line)
                            str.setSpan(BackgroundColorSpan(Color.GREEN), 0, line.length, 0)
                            isGreen = true
                            isRed = false
                            str
                        }
                        line.startsWith("-") -> {
                            val str = SpannableString(line)
                            str.setSpan(BackgroundColorSpan(Color.RED), 0, line.length, 0)
                            isRed = true
                            isGreen = false
                            str
                        }
                        else -> {
                            isGreen = false
                            isRed = false
                            SpannableString(line)
                        }
            items.add(
                DiffRecyclerItem.SectionTwoFiles(
                    lineNumberRange,
                    firstCommitLines,
                    secondCommitLines
            )